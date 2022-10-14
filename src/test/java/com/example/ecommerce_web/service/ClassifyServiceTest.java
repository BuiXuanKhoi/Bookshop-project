package com.example.ecommerce_web.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;

import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.model.entities.Category;
import com.example.ecommerce_web.model.entities.Classify;
import com.example.ecommerce_web.repository.CategoryRepository;
import com.example.ecommerce_web.repository.ClassifyRepository;
import com.example.ecommerce_web.service.impl.ClassifyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class ClassifyServiceTest {


    ClassifyRepository classifyRepository;
    ClassifyServiceImpl classifyService;
    CategoryService categoryService;

    @BeforeEach
    void initTest(){
        classifyRepository = mock(ClassifyRepository.class);
        categoryService = mock(CategoryService.class);
        classifyService = new ClassifyServiceImpl(classifyRepository, categoryService);
    }



    @Test
    void whenUpdateClassifyWithBook_thenReturnUpdatedClassify(){
        Classify classify = mock(Classify.class);
        Books books = mock(Books.class);

        classify.setBooks(books);
        classifyRepository.save(classify);
        verify(classify, times(1)).setBooks(books);
        verify(classifyRepository, times(1)).save(classify);
    }


    @Test
    void whenCreateClassify_thenReturnNewClassify(){
        int categoryId = 1;
        Category category = mock(Category.class);
        Classify classify = new Classify(0 , null, category);
        Classify savedClassify = mock(Classify.class);

        when(categoryService.findById(categoryId)).thenReturn(category);
        when(classifyRepository.save(classify)).thenReturn(savedClassify);

        assertThat(classifyService.createClassify(categoryId), is(savedClassify));
    }
}
