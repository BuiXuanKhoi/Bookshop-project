package com.example.ecommerce_web.controller;


import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.mapper.*;
import com.example.ecommerce_web.model.dto.request.*;
import com.example.ecommerce_web.model.dto.respond.*;
import com.example.ecommerce_web.model.entities.Author;
import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.model.entities.CartItem;
import com.example.ecommerce_web.model.entities.Feedback;
import com.example.ecommerce_web.service.AuthorService;
import com.example.ecommerce_web.service.BookService;
import com.example.ecommerce_web.service.CategoryService;
import com.example.ecommerce_web.service.FeedbackService;
import com.example.ecommerce_web.validator.ListValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(maxAge = 3600, origins = "*")
public class BookController {

    BookService bookService;
    FeedbackService feedbackService;
    CategoryService categoryService;
    BookMapper bookMapper;
    FeedbackMapper feedbackMapper;
    CartItemMapper cartItemMapper;
    AuthorService authorService;
    AuthorMapper authorMapper;

    @Autowired
    public BookController(BookService bookService, FeedbackService feedbackService
            , CategoryService categoryService, BookMapper bookMapper, FeedbackMapper feedbackMapper,
                          CartItemMapper cartItemMapper, AuthorService authorService, AuthorMapper authorMapper) {
        this.bookService = bookService;
        this.feedbackService = feedbackService;
        this.categoryService = categoryService;
        this.bookMapper = bookMapper;
        this.feedbackMapper = feedbackMapper;
        this.cartItemMapper = cartItemMapper;
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping(produces = "application/json", consumes = "multipart/form-data")
    public BookRespondDTO addNewBook(
            @RequestParam( value = "data", required = false) String data,
            @RequestParam( value = "image", required = false) MultipartFile multipartFile) {
        ObjectMapper objectMapper = new ObjectMapper();
        BookRequestDTO bookRequestDTO = null;

        try
        {
            bookRequestDTO = objectMapper.readValue(data, BookRequestDTO.class);
            Books books = this.bookService.add(bookRequestDTO, multipartFile);
            return bookMapper.toDTO(books);
        }
        catch (Exception exception)
        {
            throw new ResourceNotFoundException(exception.getMessage());
        }

    }

    @PutMapping("/{id}")
    public BookRespondDTO editBook(
            @PathVariable int id,
            @RequestBody @Valid ModifyBookRequestDTO modifyBookRequestDTO){
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
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "mode", required = false, defaultValue = "na") String mode,
            @RequestParam(name = "author", required = false, defaultValue = "0") String author){
        return this.bookService.getPage(searchCode,filter,mode,page, author);
    }

    @GetMapping("/manage")
    public Page<BookFeatureRespondDTO> getPageManageBook(
            @RequestParam(name = "search", required = false, defaultValue = "%") String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page
    ){
        return this.bookService.getPageManageBook(search, page);
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
            @RequestBody @Valid FeedbackRequestDTO feedbackRequestDTO
    ){
        Feedback savedFeedback = this.feedbackService.add(feedbackRequestDTO, bookId);
        return feedbackMapper.toDTO(savedFeedback);
    }

    @GetMapping("/{id}/feedbacks/rate")
    public RateCountingRespondDTO getRateCounter(@PathVariable Integer id){
        return this.feedbackService.countRatingPoint(id);
    }

    @GetMapping("/{id}/feedbacks")
    public Page<FeedbackRespondDTO> getPageFeedback(
            @PathVariable int id,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @RequestParam(name = "mode", defaultValue = "a", required = false) char mode,
            @RequestParam(name = "filter", defaultValue = "0", required = false) float filter){
        return this.feedbackService.getPageByBook(page, size, mode, filter, id);
    }



    @Operation(
            summary = "Get Book Detail By ID",
            description ="When enter book ID as an path of the url. The API will return the book detail \n" +
                    "In case of the API return 404 status code, it means that the books with this ID not exist in database. " +
                    "The respond will contains fields such as : book name. image link, book price ,... "
    )
    @GetMapping("/{id}")
    public BookRespondDTO getBookDetail(@PathVariable("id") int bookId){
        Books books =  this.bookService.getById(bookId);
        return bookMapper.toDTO(books);
    }

    @Operation(
            summary = "Get Cart item by ID",
            description = "" +
                    "When enter ID as an path variable to the URL. The Server will search for the cart item by the ID entered. \n" +
                    "In case of the API returrns 404 status code, it means that the cart item with this ID not exist." +
                    "Else, the Server will returns the Cart Item as JSON value."
    )
    @GetMapping("/{id}/cart")
    public CartItemRespondDTO getCartItemExistByBook(@PathVariable int id){
        CartItem cartItem = this.bookService.getCartItemByBook(id);
        return cartItemMapper.toDTO(cartItem);
    }

    @GetMapping("/authors")
    public Page<AuthorRespondDTO> getPageAuthor(
            @RequestParam (name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "search", defaultValue = "%", required = false) String search){
        return this.authorService.getPage(page, search);
    }

    @GetMapping("/categories")
    public Page<CategoryRespondDTO> getPageCategory(
            @RequestParam (name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "search", defaultValue = "%", required = false) String search
    ){
        return this.categoryService.getPage(page, search);
    }
}
