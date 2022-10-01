package com.example.ecommerce_web.controller;


import com.example.ecommerce_web.mapper.CategoryMapper;
import com.example.ecommerce_web.model.dto.respond.CategoryRespondDTO;
import com.example.ecommerce_web.model.entities.Category;
import com.example.ecommerce_web.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(maxAge = 3600, origins = "*")
public class CategoryController {

    CategoryService categoryService;
    CategoryMapper categoryMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @PostMapping
    public CategoryRespondDTO addCategory( @RequestBody @Valid Category category){
        Category savedCategory =  this.categoryService.add(category);
        return categoryMapper.toDTO(savedCategory);
    }

    @GetMapping
    public List<CategoryRespondDTO> getListCategory(){
        return this.categoryService.findAll()
                                   .stream()
                                   .map(categoryMapper::toDTO)
                                   .collect(Collectors.toList());
    }
}
