package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.ConstraintViolateException;
import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.mapper.FeedbackMapper;
import com.example.ecommerce_web.model.dto.request.FeedbackRequestDTO;
import com.example.ecommerce_web.model.dto.respond.FeedbackRespondDTO;
import com.example.ecommerce_web.model.dto.respond.RateCountingRespondDTO;
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

    private final FeedbackRepository feedbackRepository;
    private final UserLocal userLocal;
    private final FeedbackMapper feedbackMapper;
    private final BookService bookService;
    private final UserService userService;

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

        if(feedbackRepository.existsByUsersAndBooks(users, books)){
            throw new ConstraintViolateException("User cannot rating an book two times !!!");
        }

        Feedback feedback = feedbackMapper.fromDTO(feedbackRequestDTO);
        feedback.setBooks(books);
        feedback.setUsers(users);
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
        float max = (filter == 0) ? 6 : filter + 1;

        Page<FeedbackRespondDTO> pageFeedback = this.feedbackRepository.getPageFeedback(pageable, filter, max, bookId);

        if(!pageFeedback.hasContent())  throw new ResourceNotFoundException("Page is Empty");
        return pageFeedback;
    }

    @Override
    public RateCountingRespondDTO countRatingPoint(int bookId) {
        Books books = bookService.getById(bookId);
        int counterTerribleRate = feedbackRepository.countByRatingPointIsBetweenAndBooks(0,2, books);
        int counterBadRate = feedbackRepository.countByRatingPointIsBetweenAndBooks(2,3, books);
        int counterNormalRate = feedbackRepository.countByRatingPointIsBetweenAndBooks(3,4,books);
        int counterGoodRate = feedbackRepository.countByRatingPointIsBetweenAndBooks(4, 5, books);
        int counterWonderfulRate = feedbackRepository.countByRatingPointIsBetweenAndBooks(5, 6, books);

        return RateCountingRespondDTO.builder()
                                     .terrible(counterTerribleRate)
                                     .bad(counterBadRate)
                                     .normal(counterNormalRate)
                                     .good(counterGoodRate)
                                     .wonderful(counterWonderfulRate)
                                     .build();

    }

    private Pageable createPage(int page, int size, char mode){

        Sort sort = switch (mode){
            case 'a' -> Sort.by(Sort.Direction.ASC, "createDay");
            case 'd' -> Sort.by(Sort.Direction.DESC, "createDay");
            default -> throw new ResourceNotFoundException("NOT FOUND MODE SORT !!!");
        };
        return PageRequest.of(page, size, sort);
    }
}
