package com.example.ecommerce_web.controller;

import com.example.ecommerce_web.constant.BookState;
import com.example.ecommerce_web.mapper.AuthorMapper;
import com.example.ecommerce_web.mapper.BookMapper;
import com.example.ecommerce_web.mapper.CartItemMapper;
import com.example.ecommerce_web.mapper.FeedbackMapper;
import com.example.ecommerce_web.model.dto.request.ModifyBookRequestDTO;
import com.example.ecommerce_web.model.dto.respond.BookRespondDTO;
import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.service.AuthorService;
import com.example.ecommerce_web.service.BookService;
import com.example.ecommerce_web.service.CategoryService;
import com.example.ecommerce_web.service.FeedbackService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    BookService bookService;
    FeedbackService feedbackService;
    CategoryService categoryService;
    BookMapper bookMapper;
    FeedbackMapper feedbackMapper;
    CartItemMapper cartItemMapper;
    AuthorService authorService;
    AuthorMapper authorMapper;

    @Autowired
    MockMvc mvc;

    @BeforeEach
    void initTest(){
        bookService = mock(BookService.class);
        feedbackService = mock(FeedbackService.class);
        categoryService = mock(CategoryService.class);
        bookMapper = mock(BookMapper.class);
        feedbackMapper = mock(FeedbackMapper.class);
        cartItemMapper = mock(CartItemMapper.class);
        authorService = mock(AuthorService.class);
        authorMapper = mock(AuthorMapper.class);
    }


    @Test
    void whenGetBookDetail_thenReturnStatusOkAndBookDetail_ifBookIdExisted() throws Exception {
        int bookId = 1;
        Books books = mock(Books.class);
        BookRespondDTO bookRespondDTO = mock(BookRespondDTO.class);
        when(bookService.getById(bookId)).thenReturn(books);
        when(bookMapper.toDTO(books)).thenReturn(bookRespondDTO);

        mvc.perform(MockMvcRequestBuilders.get("/api/books/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    void whenGetBookDetail_thenReturnStatusNotFound_ifBookNotExist() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/books/100000"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    void whenEditBook_thenReturnStatusOk_ifBookExist() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        int bookId = 1;
        ModifyBookRequestDTO modifyBookRequestDTO =
                ModifyBookRequestDTO.builder()
                                    .bookName("Carrie")
                                    .bookPrice(12.0f)
                                    .quantity(10)
                                    .description("Test")
                                    .state("UNAVAILABLE").build();
        BookRespondDTO bookRespondDTO = mock(BookRespondDTO.class);
        Books savedBook = mock(Books.class);

        when(bookService.update(bookId, modifyBookRequestDTO)).thenReturn(savedBook);
        when(bookMapper.toDTO(savedBook)).thenReturn(bookRespondDTO);

        mvc.perform(MockMvcRequestBuilders.put("/api/books/1").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modifyBookRequestDTO))
                .with(user("khoiproviphehe").roles("ADMIN"))
        )
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    void whenEditBook_thenReturnStatusIsNotFound_ifBookNotExist() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        ModifyBookRequestDTO modifyBookRequestDTO =
                ModifyBookRequestDTO.builder()
                        .bookName("Carrie")
                        .bookPrice(12.0f)
                        .quantity(10)
                        .description("Test")
                        .state("UNAVAILABLE").build();
        mvc.perform(MockMvcRequestBuilders.put("/api/books/100000")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modifyBookRequestDTO))
                .with(user("khoiproviphehe").roles("ADMIN"))
        )
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    void whenGetTopPopular_thenReturnStatusOkAndListBooks_ifExist() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/books/popular"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(8))
                .andDo(print());
    }


    @Test
    void whenGetTopOnSale_thenReturnStatusOkAndListOnSaleLimit10_ifExist() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/api/books/onsale"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(10))
                .andDo(print());
    }


}
