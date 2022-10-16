package com.example.ecommerce_web.controller;


import com.example.ecommerce_web.mapper.CategoryMapper;
import com.example.ecommerce_web.model.dto.respond.CategoryRespondDTO;
import com.example.ecommerce_web.model.entities.Category;
import com.example.ecommerce_web.service.CategoryService;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {


    @Autowired
    MockMvc mockMvc;

    CategoryService categoryService;
    CategoryMapper categoryMapper;
    ObjectMapper objectMapper;

    @BeforeEach
    void initTest(){
        categoryService = mock(CategoryService.class);
        categoryMapper = mock(CategoryMapper.class);
        objectMapper = new ObjectMapper();
    }



    @Test
    void whenAddCategory_thenReturnStatusOk_ifRequestValid() throws Exception{
        Category savedCategory = mock(Category.class);
        CategoryRespondDTO categoryRespondDTO = mock(CategoryRespondDTO.class);

        Category category = new Category("Adult", "Contain adult 18+ content");

        when(categoryService.add(category)).thenReturn(savedCategory);
        when(categoryMapper.toDTO(savedCategory)).thenReturn(categoryRespondDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category))
                .with(user("khoiproviphehe").roles("ADMIN"))
        )
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    void whenGetAll_thenReturnListCategory_ifExist() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
