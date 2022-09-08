package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.entities.Author;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AuthorService {


    ResponseEntity<?> addAuthor(Author author);

    List<Author> getListAuthor();
}
