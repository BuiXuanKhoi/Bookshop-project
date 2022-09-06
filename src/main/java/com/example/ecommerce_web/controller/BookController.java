package com.example.ecommerce_web.controller;


import com.example.ecommerce_web.model.dto.request.AddBookRequest;
import com.example.ecommerce_web.model.dto.request.EditBookDTO;
import com.example.ecommerce_web.model.dto.request.FeedbackRequestDTO;
import com.example.ecommerce_web.model.dto.respond.BookFeatureRespondDTO;
import com.example.ecommerce_web.model.dto.respond.FeedbackRespondDTO;
import com.example.ecommerce_web.service.BookService;
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

    @Autowired
    public BookController(BookService bookService, FeedbackService feedbackService) {
        this.bookService = bookService;
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public ResponseEntity<?> addNewBook(@RequestBody AddBookRequest addBookRequest){
        return this.bookService.addNewBook(addBookRequest);
    }

    @PutMapping
    public ResponseEntity<?> editBook(@RequestBody EditBookDTO editBookDTO){
        return this.bookService.editBook(editBookDTO);
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

    @PostMapping("/{id}")
    public FeedbackRespondDTO giveFeedback(
            @PathVariable("id") int bookId,
            @RequestBody FeedbackRequestDTO feedbackRequestDTO
    ){
        return this.feedbackService.giveFeedback(feedbackRequestDTO, bookId);
    }
}
