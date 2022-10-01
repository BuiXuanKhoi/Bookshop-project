package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.dto.respond.AuthorRespondDTO;
import com.example.ecommerce_web.model.entities.Author;
import com.example.ecommerce_web.validator.ListValidator;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AuthorService {
    Author add(Author author);
    Author getById(int id);
    List<Author> findAll();
}
