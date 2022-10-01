package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.dto.respond.AuthorRespondDTO;
import com.example.ecommerce_web.model.dto.respond.MessageRespond;
import com.example.ecommerce_web.model.entities.Author;
import com.example.ecommerce_web.model.entities.Category;
import com.example.ecommerce_web.repository.AuthorRepository;
import com.example.ecommerce_web.service.AuthorService;
import com.example.ecommerce_web.validator.ListValidator;
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

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author add(Author author){
        return this.authorRepository.save(author);
    }

    @Override
    public Author getById(int id) {
        return this.authorRepository.findById(id)
                                    .orElseThrow(
                             () -> new ResourceNotFoundException("Not Found Author with ID: " + id));
    }

    @Override
    public List<Author> findAll() {
        List<Author> listAuthor = this.authorRepository.findAll();
        return ListValidator.ofList(listAuthor).ifNotEmpty();

    }


}
