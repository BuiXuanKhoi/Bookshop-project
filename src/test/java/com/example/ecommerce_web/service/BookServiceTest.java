package com.example.ecommerce_web.service;

import com.example.ecommerce_web.constant.BookState;
import com.example.ecommerce_web.exceptions.ConstraintViolateException;
import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.mapper.BookMapper;
import com.example.ecommerce_web.model.dto.request.ModifyBookRequestDTO;
import com.example.ecommerce_web.model.dto.respond.BookRespondDTO;
import com.example.ecommerce_web.model.entities.Author;
import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.model.entities.Category;
import com.example.ecommerce_web.model.entities.Classify;
import com.example.ecommerce_web.repository.*;
import com.example.ecommerce_web.security.service.UserLocal;
import com.example.ecommerce_web.service.impl.AuthorServiceImpl;
import com.example.ecommerce_web.service.impl.BookServiceImpl;
import com.example.ecommerce_web.validator.ListValidator;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


import java.awt.print.Book;
import java.lang.invoke.CallSite;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

public class BookServiceTest {

    BookRepository bookRepository;
    ModelMapper modelMapper;
    AuthorService authorService;
    AuthorRepository authorRepository;
    UserLocal userLocal;
    CategoryRepository categoryRepository;
    BookServiceImpl bookService;
    ClassifyService classifyService;
    BookMapper bookMapper;
    UserService userService;
    CloudinaryService cloudinaryService;
    ClassifyRepository classifyRepository;



    @BeforeEach
    public void init() {
        bookRepository = mock(BookRepository.class);
        modelMapper = mock(ModelMapper.class);
        authorService = mock(AuthorServiceImpl.class);
        authorRepository = mock(AuthorRepository.class);
        userLocal = mock(UserLocal.class);
        categoryRepository = mock(CategoryRepository.class);
        bookMapper = mock(BookMapper.class);
        userService = mock(UserService.class);
        classifyService = mock(ClassifyService.class);
        cloudinaryService = mock(CloudinaryService.class);
        classifyRepository = mock(ClassifyRepository.class);

        bookService = new BookServiceImpl(bookRepository, authorService,
                authorRepository,
                userLocal, categoryRepository, classifyService, bookMapper, userService, cloudinaryService, classifyRepository);

    }

    @Test
    void testWhenFindAll_thenThrowException_ifListNull(){
        List<Books> books = List.of(
                Books.builder()
                     .bookName("Khoi")
                     .bookPrice(1.0f)
                     .bookState(BookState.AVAILABLE)
                     .bookId(1)
                     .createDay(new Date())
                     .updateDay(new Date())
                     .description("Dont Know")
                     .imageLink("Hello")
                     .quantity(10)
                     .ratingPoint(3.3f)
                     .review("Good")
                     .build(),

                   Books.builder()
                        .bookName("Tuan")
                        .bookPrice(1.0f)
                        .bookState(BookState.AVAILABLE)
                        .bookId(2)
                        .createDay(new Date())
                        .updateDay(new Date())
                        .description("Dont Know")
                        .imageLink("Hello")
                        .quantity(10)
                        .ratingPoint(3.3f)
                        .review("Good")
                        .build()

        );
        when(bookRepository.findAll()).thenReturn(books);
        ListValidator<Books> listValidator = ListValidator.ofList(books);
        assertThat(bookService.findAll(),is(listValidator.ifNotEmpty()));
    }


    @Test
    void testWhenFindAll_thenThrowResourceNotFoundException_ifListNull(){
        List<Books> books = new ArrayList<>();
        when(bookRepository.findAll()).thenReturn(books);
        ListValidator<Books> listValid = ListValidator.ofList(books);

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> bookService.findAll());

        assertThat(exception.getMessage(), is("List is Empty !!!"));
    }


    @Test
    void testWhenFindById_thenThrowResourceNotFoundException_ifIdNotExist(){
        when(bookRepository.findById(0)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> bookService.getById(0));
        assertThat(exception.getMessage(), is("Book Not Found With ID: 0"));
    }

    @Test
    void testWhenGetById_thenReturnBooks_ifIdExist(){
        Books books = mock(Books.class);
        when(bookRepository.findById(1)).thenReturn(Optional.of(books));
        assertThat(bookService.getById(1), is(books));
    }


    @Test
    void testWhenDelete_thenThrowResourceNotFoundException_ifIdNotExist(){
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> bookService.delete(0));
        assertThat(exception.getMessage(), is("Book Not Found With ID: 0"));
    }

    @Test
    void testWhenDelete_thenReturnStatusOk_ifIdExist(){
        Books books = mock(Books.class);
        when(bookRepository.findById(1)).thenReturn(Optional.of(books));
        bookRepository.delete(books);
        verify(bookRepository, times(1)).delete(books);
    }


//    @Test
//    void whenFindTopPopular_thenReturnTopPopular(){
//        List<Books> books = mock(List.class);
//        Stream<Books> booksStream = mock(Stream.class);
//        Stream<Books> sortedBooks = mock(Stream.class);
//        Stream<Books> streamTopPopularBooks = mock(Stream.class);
//        List<Books> topPopularBooks = mock(List.class);
//
//
//        when(bookRepository.findAll()).thenReturn(books);
//        when(books.stream()).thenReturn(booksStream);
//        when(booksStream.sorted(Comparator.comparing(Books::getQuantity))).thenReturn(sortedBooks);
//        when(sortedBooks.limit(8)).thenReturn(streamTopPopularBooks);
//        when(streamTopPopularBooks.collect(Collectors.toList())).thenReturn(topPopularBooks);
//
//        assertThat(bookService.findTopPopular(),is(topPopularBooks));
//    }

    @Test
    void whenCheckout_thenThrowConstrainViolateException_ifMinusQuantityGreaterThanBaseQuantity(){
        Books books = mock(Books.class);
        int bookId = 1;
        int quantity = 10;
        int minusQuantity = 11;

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(books));
        when(books.getQuantity()).thenReturn(quantity);

        ConstraintViolateException exception = Assertions.assertThrows(ConstraintViolateException.class,
                () -> bookService.checkout(bookId, minusQuantity));

        assertThat(exception.getMessage(), is("Cannot add to order due to out of stock books !!!"));
    }

    @Test
    void whenCheckout_thenChangeBookStateToOutOfStock_ifMinusQuantityEqualToBaseQuantity(){
        Books books = mock(Books.class);
        int bookId = 1;
        int quantity = 10;
        int minusQuantity = 10;
        Books savedBooks = mock(Books.class);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(books));
        when(books.getQuantity()).thenReturn(quantity);

        books.setBookState(BookState.OUT_OF_STOCK);
        books.setQuantity(0);

        verify(books).setBookState(BookState.OUT_OF_STOCK);
        verify(books).setQuantity(0);

        when(bookRepository.save(books)).thenReturn(savedBooks);

        assertThat(bookService.checkout(bookId, minusQuantity), is(savedBooks));
        assertThat(bookService.checkout(bookId, minusQuantity).getQuantity(), is(0));
    }

    @Test
    void whenUpdateBook_thenReturnBookAfterUpdate(){
        Books books = mock(Books.class);
        Books mappedBooks = mock(Books.class);
        Books savedBooks = mock(Books.class);
        int bookId = 1;
        ModifyBookRequestDTO modifyBookRequestDTO = mock(ModifyBookRequestDTO.class);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(books));
        when(bookMapper.toExistedBooks(modifyBookRequestDTO, books)).thenReturn(mappedBooks);
        when(this.bookRepository.save(mappedBooks)).thenReturn(savedBooks);

        assertThat(bookService.update(bookId, modifyBookRequestDTO), is(savedBooks));
    }
}
