package com.example.ecommerce_web.controller;


import com.example.ecommerce_web.model.dto.request.*;
import com.example.ecommerce_web.model.dto.respond.BookFeatureRespondDTO;
import com.example.ecommerce_web.model.dto.respond.BookRespondDTO;
import com.example.ecommerce_web.model.dto.respond.FeedbackRespondDTO;
import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.model.entities.Category;
import com.example.ecommerce_web.service.BookService;
import com.example.ecommerce_web.service.CategoryService;
import com.example.ecommerce_web.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    BookService bookService;
    FeedbackService feedbackService;
    CategoryService categoryService;

    @Autowired
    public BookController(BookService bookService, FeedbackService feedbackService,CategoryService categoryService) {
        this.bookService = bookService;
        this.feedbackService = feedbackService;
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<?> addNewBook(@RequestBody BookRequestDTO bookRequestDTO){
        return this.bookService.addNewBook(bookRequestDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editBook(
            @PathVariable int id,
            @RequestBody ModifyBookRequestDTO modifyBookRequestDTO){
        return this.bookService.editBook(id, modifyBookRequestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable int bookId){
        return this.bookService.deleteBook(bookId);
    }

//    @PostMapping("/category")
//    public ResponseEntity<?> addCategory(@RequestBody Category category){
//        return this.categoryService.addCategory(category);
//    }

    @GetMapping
    public Page<BookFeatureRespondDTO> getPageBook(
            @RequestParam(name = "filter", required = false, defaultValue = "0") String filter,
            @RequestParam(name = "searchCode", required = false, defaultValue = "") String searchCode,
            @RequestParam(name = "page", required = false, defaultValue = "0") String page,
            @RequestParam(name = "mode", required = false, defaultValue = "na") String mode){
        int pageConverted = Integer.parseInt(page);
        return this.bookService.getPageBook(searchCode,filter,mode,pageConverted);
    }

    @PostMapping("/{id}")
    public FeedbackRespondDTO giveFeedback(
            @PathVariable("id") int bookId,
            @RequestBody FeedbackRequestDTO feedbackRequestDTO
    ){
        return this.feedbackService.giveFeedback(feedbackRequestDTO, bookId);
    }

    @GetMapping("/{id}")
    public BookRespondDTO getBookDetail(@PathVariable("id") int bookId){
        return this.bookService.getBookDetail(bookId);
    }
}
