package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.dto.respond.AuthorRespondDTO;
import com.example.ecommerce_web.model.dto.respond.MessageRespond;
import com.example.ecommerce_web.model.entities.Author;
import com.example.ecommerce_web.repository.AuthorRepository;
import com.example.ecommerce_web.service.AuthorService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    AuthorRepository authorRepository;
    ModelMapper modelMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }



    @Override
    public ResponseEntity<?> addAuthor(Author author){

        this.authorRepository.save(author);

        return ResponseEntity.ok(new MessageRespond(HttpStatus.CREATED.value(), "New author has been added !!!"));
    }

    @Override
    public List<AuthorRespondDTO> getListAuthor() {
        List<Author> listAuthor = this.authorRepository.findAll();

        if(listAuthor.isEmpty()){
            throw new ResourceNotFoundException("Don't have any author available !!!");
        }

        List<AuthorRespondDTO> listAuthorRespond = listAuthor.stream()
                                                             .map(author -> modelMapper.map(author, AuthorRespondDTO.class))
                                                             .collect(Collectors.toList());

        return listAuthorRespond;
    }


}
