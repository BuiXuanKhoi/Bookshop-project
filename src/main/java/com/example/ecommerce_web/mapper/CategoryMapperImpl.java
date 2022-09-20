package com.example.ecommerce_web.mapper;

import com.example.ecommerce_web.model.dto.request.CategoryRequestDTO;
import com.example.ecommerce_web.model.dto.respond.CategoryRespondDTO;
import com.example.ecommerce_web.model.entities.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapperImpl implements CategoryMapper{

    ModelMapper modelMapper;

    @Autowired
    public CategoryMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryRespondDTO toDTO(Category entity) {
        return modelMapper.map(entity, CategoryRespondDTO.class);
    }

}
