package com.example.ecommerce_web.service;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.dto.respond.BookRespondDTO;
import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.model.entities.Category;
import com.example.ecommerce_web.model.entities.Classify;
import com.example.ecommerce_web.repository.*;
import com.example.ecommerce_web.security.service.UserLocal;
import com.example.ecommerce_web.service.impl.AuthorServiceImpl;
import com.example.ecommerce_web.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.modelmapper.ModelMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

public class BookServiceTest {

    BookRepository bookRepository;
    ModelMapper modelMapper;
    AuthorService authorService;
    ClassifyRepository classifyRepository;
    AuthorRepository authorRepository;
    UserRepository userRepository;
    UserLocal userLocal;
    CategoryRepository categoryRepository;
    FeedbackRepository feedbackRepository;
    BookServiceImpl bookService;
    ClassifyService classifyService;


    @BeforeEach
    public void init() {
        bookRepository = mock(BookRepository.class);
        modelMapper = mock(ModelMapper.class);
        authorService = mock(AuthorServiceImpl.class);
        classifyRepository = mock(ClassifyRepository.class);
        authorRepository = mock(AuthorRepository.class);
        userRepository = mock(UserRepository.class);
        userLocal = mock(UserLocal.class);
        categoryRepository = mock(CategoryRepository.class);
        feedbackRepository = mock(FeedbackRepository.class);
        bookService = new BookServiceImpl(bookRepository, modelMapper,
                authorService, classifyRepository, authorRepository, userRepository,
                userLocal, categoryRepository, feedbackRepository, classifyService);
        classifyService = mock(ClassifyService.class);

    }


    @Test
    public void getBookDetail_shouldThrowResourceNotFoundException_whenBookIdNotFound(){
        when(bookRepository.findById(0)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> bookService.getBookDetail(0)
        );

        assertThat(exception.getMessage(), is("Not Found Book With ID: 0"));
    }

    @Test
    public void getBookDetail_shouldReturnBookRespondDTO_whenBookIdFound(){

        Books books = mock(Books.class);
        Category category = mock(Category.class);

        Optional<Books> booksOptional = Optional.of(books);

        when(bookRepository.findById(20)).thenReturn(booksOptional);

        books = booksOptional.get();

        String authorName = "Fujiko F Fujio";
        String categoryName = "Child Book";
        Classify classify = mock(Classify.class);
        List<Classify> classifies = Arrays.asList(classify);
        List<String> categoryNameList = Arrays.asList(categoryName);

        when(books.getClassifies()).thenReturn(classifies);

        when(classify.getCategory()).thenReturn(category);
        when(category.getCategoryName()).thenReturn(categoryName);

        BookRespondDTO bookRespondDTO = mock(BookRespondDTO.class);

        when(modelMapper.map(books, BookRespondDTO.class)).thenReturn(bookRespondDTO);

        doNothing().when(bookRespondDTO).setAuthorName(authorName);
        doNothing().when(bookRespondDTO).setCategoryName(categoryNameList);

//        verify(bookRespondDTO, times(1)).setAuthorName(anyString());
//        verify(bookRespondDTO, times(1)).setCategoryName(categoryNameList);

        Assertions.assertEquals(bookService.getBookDetail(20), bookRespondDTO);

    }




}
