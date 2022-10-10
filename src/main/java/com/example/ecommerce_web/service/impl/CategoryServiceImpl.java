package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.dto.respond.CategoryRespondDTO;
import com.example.ecommerce_web.model.entities.Category;
import com.example.ecommerce_web.repository.CategoryRepository;
import com.example.ecommerce_web.service.CategoryService;
import com.example.ecommerce_web.validator.ListValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category add(Category category){
        return this.categoryRepository.save(category);
    }

    @Override
    public List<Category> findAll() {
        List<Category> listCategory =  this.categoryRepository.findAll();
        ListValidator<Category> listValidator = ListValidator.ofList(listCategory);
        return listValidator.ifNotEmpty();
    }

    @Override
    public Category findById(int id) {
        return this.categoryRepository.findById(id)
                                      .orElseThrow(
                                              () -> new ResourceNotFoundException("Not Found Category With ID: " + id));
    }

    @Override
    public Page<CategoryRespondDTO> getPage(int page, String search) {
        final int PAGE_SIZE = 10;
        search = search.toLowerCase();
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return this.categoryRepository.getPage(pageable, search);
    }

}
