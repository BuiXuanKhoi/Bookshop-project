package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.dto.respond.CategoryRespondDTO;
import com.example.ecommerce_web.model.dto.respond.MessageRespond;
import com.example.ecommerce_web.model.entities.Category;
import com.example.ecommerce_web.repository.CategoryRepository;
import com.example.ecommerce_web.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;
    ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper){
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<?> addCategory(Category category){
 
        this.categoryRepository.save(category);

        return ResponseEntity.ok(new MessageRespond(HttpStatus.CREATED.value(), "New category has been added !!!"));
    }

    @Override
    public List<CategoryRespondDTO> getListCategory() {
        List<Category> listCategory =  this.categoryRepository.findAll();

        if(listCategory.isEmpty()){
            throw new ResourceNotFoundException("There no category available !!!");
        }

        List<CategoryRespondDTO> listCategoryRespond = listCategory.stream()
                                                                   .map(category -> modelMapper.map(category, CategoryRespondDTO.class))
                                                                   .collect(Collectors.toList());

        return listCategoryRespond;
    }

}
