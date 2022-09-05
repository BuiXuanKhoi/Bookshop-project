package com.example.ecommerce_web.controller;


import com.example.ecommerce_web.model.dto.request.AddBookRequest;
import com.example.ecommerce_web.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {

    BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<?> addNewBook(@RequestBody AddBookRequest addBookRequest){
        return this.bookService.addNewBook(addBookRequest);
    }
}
