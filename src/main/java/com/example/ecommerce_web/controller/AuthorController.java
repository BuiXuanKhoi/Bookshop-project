package com.example.ecommerce_web.controller;

import com.example.ecommerce_web.model.dto.respond.AuthorRespondDTO;
import com.example.ecommerce_web.model.entities.Author;
import com.example.ecommerce_web.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorController {
    AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<?> addAuthor(@RequestBody Author author){
        return this.authorService.addAuthor(author);
    }

    @GetMapping
    public List<AuthorRespondDTO> getListAuthor(){
        return this.authorService.getListAuthor();
    }
}
