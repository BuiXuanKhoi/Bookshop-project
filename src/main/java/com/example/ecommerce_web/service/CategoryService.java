package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.dto.request.AddCategoryDTO;
import org.springframework.http.ResponseEntity;

public interface CategoryService {

    public ResponseEntity<?> addCategory(AddCategoryDTO addCategoryDTO);
}
