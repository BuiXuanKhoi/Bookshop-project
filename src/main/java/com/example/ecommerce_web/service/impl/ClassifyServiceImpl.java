package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.model.entities.Category;
import com.example.ecommerce_web.model.entities.Classify;
import com.example.ecommerce_web.repository.CategoryRepository;
import com.example.ecommerce_web.repository.ClassifyRepository;
import com.example.ecommerce_web.service.ClassifyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.Collection;
import java.util.List;
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
        Category category = this.categoryRepository.findById(categoryId)
                                                   .orElseThrow(
                                            () -> new ResourceNotFoundException("Not Found Category With ID: " + categoryId));

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
