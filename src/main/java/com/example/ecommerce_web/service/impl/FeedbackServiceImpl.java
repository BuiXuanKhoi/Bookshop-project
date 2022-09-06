package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.ConstraintViolateException;
import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.dto.request.FeedbackRequestDTO;
import com.example.ecommerce_web.model.dto.respond.FeedbackRespondDTO;
import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.model.entities.Feedback;
import com.example.ecommerce_web.model.entities.Users;
import com.example.ecommerce_web.repository.BookRepository;
import com.example.ecommerce_web.repository.FeedbackRepository;
import com.example.ecommerce_web.repository.UserRepository;
import com.example.ecommerce_web.security.service.UserLocal;
import com.example.ecommerce_web.service.FeedbackService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    FeedbackRepository feedbackRepository;
    UserLocal userLocal;
    BookRepository bookRepository;
    ModelMapper modelMapper;
    UserRepository userRepository;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, UserLocal userLocal,
                               BookRepository bookRepository, ModelMapper modelMapper, UserRepository userRepository){
        this.feedbackRepository = feedbackRepository;
        this.userLocal = userLocal;
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }


    @Override
    public FeedbackRespondDTO giveFeedback(FeedbackRequestDTO feedbackRequestDTO, int bookId) {

        String userName = userLocal.getLocalUserName();
        Date now = new Date();
        Feedback feedback = modelMapper.map(feedbackRequestDTO, Feedback.class);
        Users users = this.userRepository.findUserByUserName(userName).get();
        Optional<Books> books = this.bookRepository.findById(bookId);

        books.orElseThrow(
                () -> new ResourceNotFoundException("Not Found Book With ID: " + bookId )
        );

        Optional<Feedback> existedFeedback = this.feedbackRepository.getFeedbackByUserAndBook(bookId, users.getUserId());

        if(existedFeedback.isPresent()){
            throw new ConstraintViolateException("User cannot rating an book two times !!!");
        }



        feedback.setBooks(books.get());
        feedback.setUsers(users);
        feedback.setCreateDay(now);

        Feedback savedFeedback = this.feedbackRepository.save(feedback);

        FeedbackRespondDTO feedbackRespond = modelMapper.map(savedFeedback, FeedbackRespondDTO.class);
        feedbackRespond.setUserName(userName);

        return feedbackRespond;
    }
}
