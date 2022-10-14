package com.example.ecommerce_web.service;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.entities.Author;
import com.example.ecommerce_web.model.entities.Category;
import com.example.ecommerce_web.repository.CategoryRepository;
import static org.mockito.Mockito.*;

import com.example.ecommerce_web.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.is;
public class CategoryServiceTest {


    CategoryRepository categoryRepository;
    CategoryServiceImpl categoryService;

    @BeforeEach
    public void initTest(){
        categoryRepository = mock(CategoryRepository.class);
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    void whenAddCategory_thenReturnNewAuthor(){
        Category category = mock(Category.class);
        Category savedCategory = mock(Category.class);
        when(categoryRepository.save(category)).thenReturn(savedCategory);
        assertThat(categoryService.add(category), is(savedCategory));
    }

    @Test
    void whenFindAll_thenThrowException_ifListEmpty(){
        List<Category> categories = new ArrayList<>();
        when(categoryRepository.findAll()).thenReturn(categories);
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> categoryService.findAll()
        );
        assertThat(exception.getMessage(), is("List is Empty !!!"));
    }

    @Test
    void whenFindAll_thenReturnList_ifListNotEmpty(){
        List<Category> categories = List.of(
                Category.builder()
                        .categoryName("Comedy")
                        .categoryId(1)
                        .categoryDescription("Including funny stories")
                        .classifies(null)
                        .build(),
                Category.builder()
                        .categoryName("Literature")
                        .categoryId(2)
                        .categoryDescription("Including good stories")
                        .classifies(null)
                        .build()
        );

        when(categoryRepository.findAll()).thenReturn(categories);
        assertThat(categoryService.findAll(), is(categories));
    }

    @Test
    void whenFindById_thenThrowResourceNotFoundException_ifIdNotExist(){
        when(categoryRepository.findById(1)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> categoryService.findById(1));
        assertThat(exception.getMessage(), is("Not Found Category With ID: 1"));
    }

    @Test
    void whenFindById_thenReturnCategory_ifIdExisted(){
        Category category = mock(Category.class);
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        assertThat(categoryService.findById(1),is(category));
    }
}
