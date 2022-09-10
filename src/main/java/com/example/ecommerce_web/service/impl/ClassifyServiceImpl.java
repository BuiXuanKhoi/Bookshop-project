package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.entities.Category;
import com.example.ecommerce_web.model.entities.Classify;
import com.example.ecommerce_web.repository.CategoryRepository;
import com.example.ecommerce_web.repository.ClassifyRepository;
import com.example.ecommerce_web.service.ClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClassifyServiceImpl implements ClassifyService {
    ClassifyRepository classifyRepository;
    CategoryRepository categoryRepository;

    @Autowired
    public ClassifyServiceImpl(ClassifyRepository classifyRepository, CategoryRepository categoryRepository){
        this.classifyRepository = classifyRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Classify createClassify(int categoryId) {
        Optional<Category> categoryOptional = this.categoryRepository.findById(categoryId);

        categoryOptional.orElseThrow(
                () -> new ResourceNotFoundException("Category Not Exist !!!!")
        );

        Classify classify = new Classify(0,null, categoryOptional.get());
        return this.classifyRepository.save(classify) ;
    }
}
