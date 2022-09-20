package com.example.ecommerce_web.mapper;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.dto.request.CartItemRequestDTO;
import com.example.ecommerce_web.model.dto.respond.CartItemRespondDTO;
import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.model.entities.CartItem;
import com.example.ecommerce_web.repository.BookRepository;
import com.example.ecommerce_web.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class CartItemMapperImpl implements CartItemMapper{

    BookService bookService;
    ModelMapper modelMapper;
    BookRepository bookRepository;

    @Autowired
    public CartItemMapperImpl(BookService bookService, ModelMapper modelMapper, BookRepository bookRepository) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
        this.bookRepository = bookRepository;
    }

    @Override
    public CartItemRespondDTO toDTO(CartItem entity) {
        Books book = entity.getBooks();
        return CartItemRespondDTO.builder()
                                 .quantity(entity.getQuantity())
                                 .cartItemsID(entity.getCartItemsID())
                                 .bookName(book.getBookName())
                                 .imageLink(book.getImageLink())
                                 .price(book.getBookPrice())
                                 .build();
    }

    @Override
    public CartItem fromDTO(CartItemRequestDTO requestDTO) {
        int bookId = requestDTO.getBookId();
        Books books = this.bookRepository.findById(bookId).orElseThrow(
                () -> new ResourceNotFoundException("Not Found Book With ID: " + bookId)
        );
        CartItem cartItem = modelMapper.map(requestDTO, CartItem.class);
        cartItem.setBooks(books);
        return cartItem;
    }
}
