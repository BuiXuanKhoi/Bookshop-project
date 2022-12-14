package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.dto.respond.CategoryRespondDTO;
import com.example.ecommerce_web.model.entities.Category;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface CategoryService {
    Category add(Category category);
    List<Category> findAll();
    Category findById(int id);
    Page<CategoryRespondDTO> getPage(int page, String search);
}
