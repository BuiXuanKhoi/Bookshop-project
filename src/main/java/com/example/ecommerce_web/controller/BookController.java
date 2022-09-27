package com.example.ecommerce_web.controller;


import com.example.ecommerce_web.mapper.BookMapper;
import com.example.ecommerce_web.mapper.BookMapperImpl;
import com.example.ecommerce_web.mapper.CartItemMapper;
import com.example.ecommerce_web.mapper.FeedbackMapper;
import com.example.ecommerce_web.model.dto.request.*;
import com.example.ecommerce_web.model.dto.respond.*;
import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.model.entities.CartItem;
import com.example.ecommerce_web.model.entities.Feedback;
import com.example.ecommerce_web.service.BookService;
import com.example.ecommerce_web.service.CategoryService;
import com.example.ecommerce_web.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {

    BookService bookService;
    FeedbackService feedbackService;
    CategoryService categoryService;
    BookMapper bookMapper;
    FeedbackMapper feedbackMapper;
    CartItemMapper cartItemMapper;

    @Autowired
    public BookController(BookService bookService, FeedbackService feedbackService
            , CategoryService categoryService, BookMapper bookMapper, FeedbackMapper feedbackMapper, CartItemMapper cartItemMapper) {
        this.bookService = bookService;
        this.feedbackService = feedbackService;
        this.categoryService = categoryService;
        this.bookMapper = bookMapper;
        this.feedbackMapper = feedbackMapper;
        this.cartItemMapper = cartItemMapper;
    }

    @PostMapping
    public BookRespondDTO addNewBook(@RequestBody BookRequestDTO bookRequestDTO){
        Books books = this.bookService.add(bookRequestDTO);
        return bookMapper.toDTO(books);
    }

    @PutMapping("/{id}")
    public BookRespondDTO editBook(
            @PathVariable int id,
            @RequestBody ModifyBookRequestDTO modifyBookRequestDTO){
        Books books = this.bookService.update(id, modifyBookRequestDTO);
        return bookMapper.toDTO(books);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable int id){
        this.bookService.delete(id);
        return ResponseEntity.ok(new MessageRespond(HttpStatus.OK.value(), "Delete Book successfully !!!"));
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

    @GetMapping("/recommend")
    public List<BookRespondDTO> getListRecommend(){
        return this.bookService.findTopRecommend()
                               .stream()
                               .map(bookMapper::toDTO)
                               .collect(Collectors.toList());
    }

    @GetMapping(value = "/popular")
    public List<BookRespondDTO> getListPopular() {
        return this.bookService.findTopPopular()
                .stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/onsale")
    public List<BookRespondDTO> getListOnSales(){
        return this.bookService.findTopOnSale()
                    .stream()
                    .map(bookMapper::toDTO)
                    .collect(Collectors.toList());
    }

    @PostMapping("/{id}/feedbacks")
    public FeedbackRespondDTO giveFeedback(
            @PathVariable("id") int bookId,
            @RequestBody FeedbackRequestDTO feedbackRequestDTO
    ){
        Feedback savedFeedback = this.feedbackService.giveFeedback(feedbackRequestDTO, bookId);
        return feedbackMapper.toDTO(savedFeedback);
    }

    @GetMapping("/{id}/feedbacks")
    public List<FeedbackRespondDTO> getListFeedback(@PathVariable int id){
        List<Feedback> listFeedback = this.feedbackService.getListFeedback(id);

        return listFeedback.stream()
                           .map(feedbackMapper::toDTO)
                           .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BookRespondDTO getBookDetail(@PathVariable("id") int bookId){
        Books books =  this.bookService.getById(bookId);
        return bookMapper.toDTO(books);
    }

    @GetMapping("/{id}/cart")
    public CartItemRespondDTO getCartItemExistByBook(@PathVariable int id){
        CartItem cartItem = this.bookService.getCartItemByBook(id);
        return cartItemMapper.toDTO(cartItem);
    }
}
