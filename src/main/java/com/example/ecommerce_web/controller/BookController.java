package com.example.ecommerce_web.controller;


import com.example.ecommerce_web.model.dto.request.AddBookRequest;
import com.example.ecommerce_web.model.dto.respond.BookFeatureRespondDTO;
import com.example.ecommerce_web.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public Page<BookFeatureRespondDTO> getPageBook(
            @RequestParam(name = "filter", required = false, defaultValue = "0") String filter,
            @RequestParam(name = "searchCode", required = false, defaultValue = "") String searchCode,
            @RequestParam(name = "page", required = false, defaultValue = "0") String page,
            @RequestParam(name = "mode", required = false, defaultValue = "na") String mode){
        int pageConverted = Integer.parseInt(page);
        return this.bookService.getPageBook(searchCode,filter,mode,pageConverted);
    }
}
