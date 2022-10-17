package com.example.ecommerce_web.controller;

import com.example.ecommerce_web.mapper.AuthorMapper;
import com.example.ecommerce_web.mapper.BookMapper;
import com.example.ecommerce_web.mapper.CartItemMapper;
import com.example.ecommerce_web.mapper.FeedbackMapper;
import com.example.ecommerce_web.model.dto.request.ModifyBookRequestDTO;
import com.example.ecommerce_web.model.dto.respond.BookRespondDTO;
import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.repository.BookRepository;
import com.example.ecommerce_web.security.jwt.JwtAuthEntryPoint;
import com.example.ecommerce_web.security.jwt.JwtUtils;
import com.example.ecommerce_web.security.service.UserDetailServiceImpl;
import com.example.ecommerce_web.service.AuthorService;
import com.example.ecommerce_web.service.BookService;
import com.example.ecommerce_web.service.CategoryService;
import com.example.ecommerce_web.service.FeedbackService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import java.security.Principal;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(BookController.class)
public class BookControllerTest {

    BookService bookService;
    FeedbackService feedbackService;
    CategoryService categoryService;
    BookMapper bookMapper;
    FeedbackMapper feedbackMapper;
    CartItemMapper cartItemMapper;
    AuthorService authorService;
    AuthorMapper authorMapper;
    SimpleGrantedAuthority admin;
    SimpleGrantedAuthority customer;
    BookRepository bookRepository;
    ObjectMapper objectMapper;

    @MockBean
    private UserDetailServiceImpl userDetailService;

    @MockBean
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    @MockBean
    private JwtUtils jwtUtils;

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
        admin = new SimpleGrantedAuthority("ADMIN");
        customer = new SimpleGrantedAuthority("CUSTOMER");
        bookRepository = mock(BookRepository.class);
        objectMapper = new ObjectMapper();
    }


    @Test
    void whenDeleteBook_thenReturnStatusNotFound_ifBookNotExisted() throws Exception{
        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/api/books/100000")
                .with(csrf())
                .with(user("lfsdfdlfsd").authorities(admin))
        )
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();

        System.out.println(result);
    }

    @Test
    void whenDeleteBook_thenReturnStatusOk_ifBookExist() throws Exception{

        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/api/books/1")
                .with(user("lfsdfdlfsd").authorities(admin))
        )
                .andExpect(status().isOk())
                .andDo(print())
        .andReturn();

        System.out.println(result);
    }


    @Test
    void whenGetPageBook_thenReturnStatusOk_ifEnoughParam() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/api/books")
                .param("filter", "0")
                .param("searchCode", "%")
                .param("page", "1")
                .param("mode", "na")
                .param("author", "0")
        )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void whenGetPageBook_thenReturnStatusNotFound_ifSearchCodeNotExist() throws Exception{
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/books")
                .param("filter", "0")
                .param("searchCode", "gfdgdghdgdf")
                .param("page", "1")
                .param("mode", "na")
                .param("author", "0")
        )
                .andExpect(status().isNotFound())
                .andReturn();

        String expected = "{\"message\" : \"This Page Is Empty !!!\"}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    void whenGetPageBook_thenReturnStatusNotFound_ifNotEnoughPage() throws Exception{
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/books")
                                                .param("filter", "0")
                                                .param("searchCode", "%")
                                                .param("page", "40")
                                                .param("mode", "na")
                                                .param("author", "0")
                                        )
                                                .andExpect(status().isNotFound())
                                                .andReturn();
        String expected = "{\"message\" : \"This Page Is Empty !!!\"}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    void whenGetPageBook_thenReturnStatusNotFound_ifModeNotExist() throws Exception{
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/books")
                                            .param("filter", "0")
                                            .param("searchCode", "%")
                                            .param("page", "40")
                                            .param("mode", "pg")
                                            .param("author", "0")
                                    )
                                            .andExpect(status().isNotFound())
                                            .andReturn();

        String expected = "{\"message\" : \"NOT FOUND MODE SORT !!!\"}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }


    @Test
    void whenGetBookDetail_thenReturnStatusOkAndBookDetail_ifBookIdExisted() throws Exception {
        int bookId = 3;
        Books books = mock(Books.class);
        BookRespondDTO bookRespondDTO = mock(BookRespondDTO.class);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(books));
        when(bookService.getById(bookId)).thenReturn(books);
        when(bookMapper.toDTO(books)).thenReturn(bookRespondDTO);

        mvc.perform(get("/api/books/{bookId}", bookId))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    void whenGetBookDetail_thenReturnStatusNotFound_ifBookNotExist() throws Exception {
        mvc.perform(get("/api/books/100000"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    void whenEditBook_thenReturnStatusOk_ifBookExist() throws Exception {
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

        when(bookRepository.findById(1)).thenReturn(Optional.of(savedBook));
        when(bookService.update(bookId, modifyBookRequestDTO)).thenReturn(savedBook);
        when(bookMapper.toDTO(savedBook)).thenReturn(bookRespondDTO);

        mvc.perform(put("/api/books/3").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modifyBookRequestDTO))
                .with(user("lfsdfdlfsd").authorities(admin))
        )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void whenEditBook_thenReturnStatusBadRequest_ifRequestBodyViolateConstrain() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        int bookId = 1;
        ModifyBookRequestDTO modifyBookRequestDTO =
                ModifyBookRequestDTO.builder()
                        .bookName(null)
                        .bookPrice(0.0f)
                        .quantity(10)
                        .description("Test")
                        .state("UNAVAILABLE").build();
        BookRespondDTO bookRespondDTO = mock(BookRespondDTO.class);
        Books savedBook = mock(Books.class);

        Principal principal = mock(Principal.class);


        when(principal.getName()).thenReturn("lfsdfdlfsd");
        when(bookService.update(bookId, modifyBookRequestDTO)).thenReturn(savedBook);
        when(bookMapper.toDTO(savedBook)).thenReturn(bookRespondDTO);

        mvc.perform(MockMvcRequestBuilders.put("/api/books/1").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modifyBookRequestDTO))
                .with(user("lfsdfdlfsd").authorities(admin))
        )
                .andExpect(status().isBadRequest())
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
                .with(user("khoiproviphehe").authorities(admin))
        )
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void whenEditBook_thenReturnStatusForBidden_ifUserNotAdmin() throws Exception{
        Principal principal = mock(Principal.class);
        ObjectMapper objectMapper = new ObjectMapper();
        ModifyBookRequestDTO modifyBookRequestDTO =
                ModifyBookRequestDTO.builder()
                        .bookName("Carrie")
                        .bookPrice(12.0f)
                        .quantity(10)
                        .description("Test")
                        .state("UNAVAILABLE").build();
        when(principal.getName()).thenReturn("tuan7");
        mvc.perform(MockMvcRequestBuilders.put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .with(user("tuan7").authorities(customer))
                .content(objectMapper.writeValueAsString(modifyBookRequestDTO))
        )
                .andExpect(status().isForbidden())
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

    @Test
    void whenGetTopRecommend_thenReturnStatusOkAndListRecommendLimit8_ifExist() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/api/books/recommend"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(8))
                .andDo(print());
    }



}
