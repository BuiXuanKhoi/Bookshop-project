package com.example.ecommerce_web.service;

import com.example.ecommerce_web.constant.BookState;
import com.example.ecommerce_web.exceptions.ConstraintViolateException;
import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.mapper.FeedbackMapper;
import com.example.ecommerce_web.model.dto.request.FeedbackRequestDTO;
import com.example.ecommerce_web.model.dto.respond.RateCountingRespondDTO;
import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.model.entities.Feedback;
import com.example.ecommerce_web.model.entities.Users;
import com.example.ecommerce_web.repository.FeedbackRepository;
import com.example.ecommerce_web.security.service.UserLocal;
import com.example.ecommerce_web.service.impl.FeedbackServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.is;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;


public class FeedbackServiceTest {

    FeedbackRepository feedbackRepository;
    UserLocal userLocal;
    FeedbackMapper feedbackMapper;
    BookService bookService;
    UserService userService;
    FeedbackServiceImpl feedbackService;

    @BeforeEach
    void initTest() {
        feedbackRepository = mock(FeedbackRepository.class);
        userLocal = mock(UserLocal.class);
        bookService = mock(BookService.class);
        userService = mock(UserService.class);
        feedbackMapper = mock(FeedbackMapper.class);
        feedbackService = new FeedbackServiceImpl(feedbackRepository, userLocal, feedbackMapper, bookService, userService);
    }


    @Test
    void whenGetById_thenThrowResourceNotFoundException_ifIdNotExist(){
        when(feedbackRepository.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> feedbackService.getById(1));

        assertThat(exception.getMessage(), is("Not Found Feedback With ID: 1"));
    }

    @Test
    void whenGetById_thenReturnFeedback_ifIdExisted(){
        Feedback feedback = mock(Feedback.class);
        when(feedbackRepository.findById(1)).thenReturn(Optional.of(feedback));
        assertThat(feedbackService.getById(1), is(feedback));
    }


    @Test
    void whenAddFeedback_thenThrowConstraintViolateException_ifFeedbackAlreadyExistedByBookAndUser(){
        FeedbackRequestDTO feedbackRequestDTO = mock(FeedbackRequestDTO.class);
        int bookId = 1;
        String userName = "Khoi";
        Books books = mock(Books.class);
        Users users = mock(Users.class);

        when(userLocal.getLocalUserName()).thenReturn(userName);
        when(userService.findByUserName(userName)).thenReturn(users);
        when(bookService.getById(bookId)).thenReturn(books);

        when(feedbackRepository.existsByUsersAndBooks(users, books)).thenReturn(true);

        ConstraintViolateException exception = Assertions.assertThrows(ConstraintViolateException.class,
                () -> feedbackService.add(feedbackRequestDTO, bookId));

        assertThat(exception.getMessage(), is("User cannot rating an book two times !!!"));
    }

    @Test
    void whenAddFeedback_thenReturnNewFeedback(){
        FeedbackRequestDTO feedbackRequestDTO = mock(FeedbackRequestDTO.class);
        int bookId = 1;
        String userName = "Khoi";
        Books books = mock(Books.class);
        Users users = mock(Users.class);
        Feedback feedback = mock(Feedback.class);
        Feedback savedFeedback = mock(Feedback.class);

        when(userLocal.getLocalUserName()).thenReturn(userName);
        when(userService.findByUserName(userName)).thenReturn(users);
        when(bookService.getById(bookId)).thenReturn(books);
        when(feedbackRepository.existsByUsersAndBooks(users, books)).thenReturn(false);
        when(feedbackMapper.fromDTO(feedbackRequestDTO)).thenReturn(feedback);
        when(feedbackRepository.save(feedback)).thenReturn(savedFeedback);

        assertThat(feedbackService.add(feedbackRequestDTO, bookId), is(savedFeedback));

    }


    @Test
    void whenFindAll_thenThrowResourceNotFoundException_ifListEmpty(){
        List<Feedback> feedbacks = new ArrayList<>();
        int bookId = 1;
        Books books = mock(Books.class);

        when(bookService.getById(1)).thenReturn(books);
        when(books.getFeedbacks()).thenReturn(feedbacks);
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> feedbackService.findAll(bookId));

        assertThat(exception.getMessage(), is("List is Empty !!!"));
    }


    @Test
    void whenFindAll_thenReturnListFeedbacByBook_ifListNotEmpty(){
        int bookId = 1;
        Books books = mock(Books.class);
        Users users = mock(Users.class);
        Users users2 = mock(Users.class);
        List<Feedback> feedbacks = List.of(
                Feedback.builder()
                        .books(books)
                        .feedbackId(1)
                        .title("Good")
                        .users(users)
                        .comment("So Good")
                        .createDay(new Date())
                        .ratingPoint(4.0f)
                        .build(),
                Feedback.builder()
                        .books(books)
                        .feedbackId(2)
                        .title("Bad")
                        .users(users2)
                        .comment("So Bad")
                        .createDay(new Date())
                        .ratingPoint(1.0f)
                        .build()
        );

        when(bookService.getById(bookId)).thenReturn(books);
        when(books.getFeedbacks()).thenReturn(feedbacks);
        assertThat(feedbackService.findAll(bookId), is(feedbacks));
    }

    @Test
    void whenCountingRatingPoint_shouldReturnRatingPointCounter(){
        int bookId = 1;
        Books books = mock(Books.class);
        int terrible = 2;
        int bad = 2;
        int normal = 2;
        int good = 2;
        int wonderful = 2;
        when(bookService.getById(bookId)).thenReturn(books);
        when(feedbackRepository.countByRatingPointIsBetweenAndBooks(0,2, books)).thenReturn(terrible);
        when(feedbackRepository.countByRatingPointIsBetweenAndBooks(2,3, books)).thenReturn(bad);
        when(feedbackRepository.countByRatingPointIsBetweenAndBooks(3,4,books)).thenReturn(normal);
        when(feedbackRepository.countByRatingPointIsBetweenAndBooks(4, 5, books)).thenReturn(good);
        when(feedbackRepository.countByRatingPointIsBetweenAndBooks(5, 6, books)).thenReturn(wonderful);
        RateCountingRespondDTO rateCountingRespondDTO = RateCountingRespondDTO.builder()
                                                                              .terrible(terrible)
                                                                              .bad(bad)
                                                                              .normal(normal)
                                                                              .good(good)
                                                                              .wonderful(wonderful).build();

        assertThat(feedbackService.countRatingPoint(bookId).getTerrible(), is(rateCountingRespondDTO.getTerrible()));
        assertThat(feedbackService.countRatingPoint(bookId).getBad(), is(rateCountingRespondDTO.getBad()));
        assertThat(feedbackService.countRatingPoint(bookId).getNormal(), is(rateCountingRespondDTO.getNormal()));
        assertThat(feedbackService.countRatingPoint(bookId).getGood(), is(rateCountingRespondDTO.getGood()));
        assertThat(feedbackService.countRatingPoint(bookId).getWonderful(), is(rateCountingRespondDTO.getWonderful()));
    }
}
