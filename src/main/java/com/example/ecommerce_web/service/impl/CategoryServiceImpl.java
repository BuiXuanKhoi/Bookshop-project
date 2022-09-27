package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.mapper.CategoryMapper;
import com.example.ecommerce_web.model.dto.respond.CategoryRespondDTO;
import com.example.ecommerce_web.model.dto.respond.MessageRespond;
import com.example.ecommerce_web.model.entities.Category;
import com.example.ecommerce_web.repository.CategoryRepository;
import com.example.ecommerce_web.service.CategoryService;
import com.example.ecommerce_web.validator.ListValidator;
import com.example.ecommerce_web.validator.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper){
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Category add(Category category){
        return this.categoryRepository.save(category);
    }

    @Override
    public List<Category> getListCategory() {
        List<Category> listCategory =  this.categoryRepository.findAll();
        ListValidator<Category> listValidator = ListValidator.ofList(listCategory);
        return listValidator.ifNotEmpty();
    }

    @Override
    public Category findById(int id) {
        return this.categoryRepository.findById(id)
                                      .orElseThrow(
                                              () -> new ResourceNotFoundException("Not Found Category With ID: " + id)
                                      );
    }

}
