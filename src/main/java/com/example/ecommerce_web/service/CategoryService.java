package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.entities.Category;
import org.springframework.http.ResponseEntity;

public interface CategoryService {

//    public ResponseEntity<?> addCategory(AddCategoryDTO addCategoryDTO);
    public ResponseEntity<?> addCategory(Category category);

}
