package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.entities.Category;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.http.ResponseEntity;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface CategoryService {

//    public ResponseEntity<?> addCategory(AddCategoryDTO addCategoryDTO);
    public ResponseEntity<?> addCategory(Category category);

    List<Category> getListCategory();

}
