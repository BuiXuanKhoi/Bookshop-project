package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.model.dto.respond.MessageRespond;
import com.example.ecommerce_web.model.entities.Category;
import com.example.ecommerce_web.repository.CategoryRepository;
import com.example.ecommerce_web.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository){

        this.categoryRepository = categoryRepository;
    }

    @Override
    public ResponseEntity<?> addCategory(Category category){
 
        this.categoryRepository.save(category);

        return ResponseEntity.ok(new MessageRespond(HttpStatus.CREATED.value(), "New category has been added !!!"));
    }

    @Override
    public List<Category> getListCategory() {
        return this.categoryRepository.findAll();
    }

}
