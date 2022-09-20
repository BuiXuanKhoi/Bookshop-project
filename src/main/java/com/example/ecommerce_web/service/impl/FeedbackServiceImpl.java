package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.ConstraintViolateException;
import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.mapper.FeedbackMapper;
import com.example.ecommerce_web.model.dto.request.FeedbackRequestDTO;
import com.example.ecommerce_web.model.dto.respond.FeedbackRespondDTO;
import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.model.entities.Feedback;
import com.example.ecommerce_web.model.entities.Users;
import com.example.ecommerce_web.repository.BookRepository;
import com.example.ecommerce_web.repository.FeedbackRepository;
import com.example.ecommerce_web.repository.UserRepository;
import com.example.ecommerce_web.security.service.UserLocal;
import com.example.ecommerce_web.service.BookService;
import com.example.ecommerce_web.service.FeedbackService;
import com.example.ecommerce_web.service.UserService;
import com.example.ecommerce_web.validator.ListValidator;
import com.example.ecommerce_web.validator.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    FeedbackRepository feedbackRepository;
    UserLocal userLocal;
    ModelMapper modelMapper;
    FeedbackMapper feedbackMapper;
    BookService bookService;
    UserService userService;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, UserLocal userLocal,
                                ModelMapper modelMapper
            ,  FeedbackMapper feedbackMapper, BookService bookService, UserService userService){
        this.feedbackRepository = feedbackRepository;
        this.userLocal = userLocal;
        this.modelMapper = modelMapper;
        this.feedbackMapper = feedbackMapper;
        this.bookService = bookService;
        this.userService = userService;
    }


    @Override
    public Feedback giveFeedback(FeedbackRequestDTO feedbackRequestDTO, int bookId) {
        String userName = userLocal.getLocalUserName();
        Users users = userService.findByUserName(userName);
        Books books = bookService.getById(bookId);
        Feedback feedback = feedbackMapper.fromDTO(feedbackRequestDTO);

        List<Feedback> listFeedbackUser = users.getFeedbacks();
        feedback.setBooks(books);
        feedback.setUsers(users);

        ifNotExistThenContinue(listFeedbackUser, books);

        return this.feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> getListFeedback(int bookId) {
        Books books = bookService.getById(bookId);
        List<Feedback> listFeedback = books.getFeedbacks();
        ListValidator<Feedback> listFeebackValidator = ListValidator.ofList(listFeedback);
        return listFeebackValidator.ifNotEmpty();
    }

    private void ifNotExistThenContinue(List<Feedback> listFeedbackUser, Books books){
        listFeedbackUser.stream()
                .filter(feedback1 -> feedback1.getBooks().equals(books))
                .findAny()
                .ifPresent(s ->
                {
                    throw new ConstraintViolateException("User cannot rating an book two times !!!");
                });
    }
}
