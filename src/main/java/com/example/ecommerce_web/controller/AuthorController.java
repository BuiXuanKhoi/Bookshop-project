package com.example.ecommerce_web.controller;

import com.example.ecommerce_web.model.entities.Author;
import com.example.ecommerce_web.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/author")
public class AuthorController {
    AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @PostMapping
    ResponseEntity<?> addAuthor(@RequestBody Author author){
        return this.authorService.addAuthor(author);
    }
}
