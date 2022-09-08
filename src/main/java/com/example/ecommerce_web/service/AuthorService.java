package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.entities.Author;
import org.springframework.http.ResponseEntity;

public interface AuthorService {

    Author addNewAuthor(Author author);

    ResponseEntity<?> addAuthor(Author author);
}
