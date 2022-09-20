package com.example.ecommerce_web.mapper;

import com.example.ecommerce_web.model.dto.respond.AuthorRespondDTO;
import com.example.ecommerce_web.model.entities.Author;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorMapperImpl implements AuthorMapper {
    ModelMapper modelMapper;

    @Autowired
    public AuthorMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthorRespondDTO toDTO(Author model) {
        return modelMapper.map(model, AuthorRespondDTO.class);
    }
}
