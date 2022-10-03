package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.ConstraintViolateException;
import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.mapper.FeedbackMapper;
import com.example.ecommerce_web.model.dto.request.FeedbackRequestDTO;
import com.example.ecommerce_web.model.dto.respond.FeedbackRespondDTO;
import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.model.entities.Feedback;
import com.example.ecommerce_web.model.entities.Users;
import com.example.ecommerce_web.repository.FeedbackRepository;
import com.example.ecommerce_web.security.service.UserLocal;
import com.example.ecommerce_web.service.BookService;
import com.example.ecommerce_web.service.FeedbackService;
import com.example.ecommerce_web.service.UserService;
import com.example.ecommerce_web.validator.ListValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    FeedbackRepository feedbackRepository;
    UserLocal userLocal;
    FeedbackMapper feedbackMapper;
    BookService bookService;
    UserService userService;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, UserLocal userLocal
            ,  FeedbackMapper feedbackMapper, BookService bookService, UserService userService){
        this.feedbackRepository = feedbackRepository;
        this.userLocal = userLocal;
        this.feedbackMapper = feedbackMapper;
        this.bookService = bookService;
        this.userService = userService;
    }


    @Override
    public Feedback getById(int id) {
        return this.feedbackRepository.findById(id)
                                      .orElseThrow(
                                              () -> new ResourceNotFoundException("Not Found Feedback With ID: " + id)
                                      );
    }

    @Override
    public Feedback add(FeedbackRequestDTO feedbackRequestDTO, int bookId) {
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
    public List<Feedback> findAll(int bookId) {
        Books books = bookService.getById(bookId);
        List<Feedback> listFeedback = books.getFeedbacks();
        ListValidator<Feedback> listFeedbackValidator = ListValidator.ofList(listFeedback);
        return listFeedbackValidator.ifNotEmpty();
    }

    @Override
    public Page<FeedbackRespondDTO> getPageByBook(int page, int size, char mode, float filter, int bookId) {
        Pageable pageable = createPage(page, size, mode);
        Page<FeedbackRespondDTO> pageFeedback = this.feedbackRepository.getPageFeedback(pageable, filter, bookId);

        if(!pageFeedback.hasContent())  throw new ResourceNotFoundException("Page is Empty");
        return pageFeedback;
    }

    private Pageable createPage(int page, int size, char mode){

        Sort sort = switch (mode){
            case 'a' -> Sort.by(Sort.Direction.ASC, "createDay");
            case 'd' -> Sort.by(Sort.Direction.DESC, "createDay");
            default -> throw new ResourceNotFoundException("NOT FOUND MODE SORT !!!");
        };
        return PageRequest.of(page, size, sort);
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
