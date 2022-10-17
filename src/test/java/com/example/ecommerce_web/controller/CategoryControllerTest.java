package com.example.ecommerce_web.controller;


import com.example.ecommerce_web.config.WebSecurityConfig;
import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.mapper.CategoryMapper;
import com.example.ecommerce_web.model.dto.respond.CategoryRespondDTO;
import com.example.ecommerce_web.model.entities.Category;
import com.example.ecommerce_web.repository.CategoryRepository;
import com.example.ecommerce_web.security.jwt.JwtAuthEntryPoint;
import com.example.ecommerce_web.security.jwt.JwtAuthTokenFilter;
import com.example.ecommerce_web.security.jwt.JwtUtils;
import com.example.ecommerce_web.security.service.UserDetailServiceImpl;
import com.example.ecommerce_web.service.CategoryService;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.securityContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;


//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    ObjectMapper objectMapper;
    SimpleGrantedAuthority admin ;


    @MockBean
    private UserDetailServiceImpl userDetailService;

    @MockBean
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private CategoryMapper categoryMapper;

    @MockBean
    private CategoryRepository categoryRepository;


    @BeforeEach
    void init(){
        objectMapper = new ObjectMapper();
        admin = new SimpleGrantedAuthority("ADMIN");
        userDetailService = mock(UserDetailServiceImpl.class);
    }

    @Test
    void whenAddCategory_thenReturnStatusOk_ifRequestValid() throws Exception{
        Category savedCategory = mock(Category.class);

        Category category = new Category("Supernatural", "Contain supernatural content");

        when(categoryService.add(category)).thenReturn(savedCategory);

        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category))
                .with(user("khoiproviphehe").authorities(admin))
        )
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    void whenGetAll_thenReturnListCategory_ifExisted() throws Exception{
        List<Category> categoryList = List.of(
                new Category("Drama", "Drama content"),
                new Category("Kids", "Good for children")
        );

        when(categoryService.findAll()).thenReturn(categoryList);
        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    void whenGetAll_thenReturnListCategory_ifExist() throws Exception{
        when(categoryService.findAll()).thenThrow(new ResourceNotFoundException("List is Empty !!!"));
        mockMvc.perform(get("/api/categories")
        )
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
