package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.model.entities.Category;
import com.example.ecommerce_web.model.entities.Classify;
import com.example.ecommerce_web.repository.CategoryRepository;
import com.example.ecommerce_web.repository.ClassifyRepository;
import com.example.ecommerce_web.service.CategoryService;
import com.example.ecommerce_web.service.ClassifyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

@Service
public class ClassifyServiceImpl implements ClassifyService {

    private final ClassifyRepository classifyRepository;
    private final CategoryService categoryService;

    @Autowired
    public ClassifyServiceImpl(ClassifyRepository classifyRepository, CategoryService categoryService){
        this.classifyRepository = classifyRepository;
        this.categoryService = categoryService;
    }


    @Override
    public Classify createClassify(int categoryId) {
        Category category = categoryService.findById(categoryId);
        Classify classify = new Classify(0,null, category);
        return this.classifyRepository.save(classify) ;
    }

    @Override
    @Modifying
    public void updateClassifyWithBook(Classify classify, Books books) {
            classify.setBooks(books);
            this.classifyRepository.save(classify);
    }
}
