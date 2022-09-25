package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.mapper.CartItemMapper;
import com.example.ecommerce_web.model.dto.request.CartItemRequestDTO;
import com.example.ecommerce_web.model.dto.respond.CartItemRespondDTO;
import com.example.ecommerce_web.model.dto.respond.MessageRespond;
import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.model.entities.CartItem;
import com.example.ecommerce_web.model.entities.Users;
import com.example.ecommerce_web.repository.BookRepository;
import com.example.ecommerce_web.repository.CartItemRepository;
import com.example.ecommerce_web.repository.UserRepository;
import com.example.ecommerce_web.security.service.UserLocal;
import com.example.ecommerce_web.service.BookService;
import com.example.ecommerce_web.service.CartItemService;
import com.example.ecommerce_web.service.UserService;
import com.example.ecommerce_web.validator.ListValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartItemServiceImpl implements CartItemService {
    CartItemRepository cartItemRepository;
    BookRepository bookRepository;
    UserLocal userLocal;
    ModelMapper modelMapper;
    UserRepository userRepository;
    CartItemMapper cartItemMapper;
    UserService userService;
    BookService bookService;

    @Autowired
    public CartItemServiceImpl(CartItemRepository cartItemRepository, BookRepository bookRepository,
                               UserLocal userLocal, ModelMapper modelMapper
            , UserRepository userRepository, CartItemMapper cartItemMapper, UserService userService, BookService bookService){
        this.cartItemRepository=cartItemRepository;
        this.bookRepository = bookRepository;
        this.userLocal = userLocal;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.cartItemMapper = cartItemMapper;
        this.userService = userService;
        this.bookService = bookService;
    }

    private CartItem findById(int id){
        return   this.cartItemRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not Found Cart Item With ID: " + id));
    }

    @Override
    public CartItem addToCart(CartItemRequestDTO cartItemRequestDTO) {
        int bookId = cartItemRequestDTO.getBookId();
        String userName = userLocal.getLocalUserName();
        int addedQuantity = cartItemRequestDTO.getQuantity();
        Users users = userService.findByUserName(userName);
        Books books = bookService.getById(bookId);

        CartItem cartItem = modelMapper.map(cartItemRequestDTO, CartItem.class);
//        users.getCartItems().stream()
//                            .filter(item -> item.getBooks().equals(books))
//                            .findAny()
//                            .ifPresent(item ->  updateCartItem(item.getCartItemsID(),cartItem, addedQuantity));
        updateAlreadyExist(users, books, cartItem, addedQuantity);

        cartItem.setBooks(books);
        cartItem.setUsers(users);
        return this.cartItemRepository.save(cartItem);
    }

    private void updateCartItem(int existedId, CartItem cartItem, int quantity){
        int existedQuantity = cartItem.getQuantity();
        int totalQuantity = existedQuantity + quantity;
        cartItem.setQuantity(totalQuantity);
        cartItem.setCartItemsID(existedId);
    }


    @Override
    public List<CartItem> getListCartItem() {
        String userName = userLocal.getLocalUserName();
        Users users = userService.findByUserName(userName);
        List<CartItem> listCartItem = users.getCartItems();
        ListValidator<CartItem> listCartItemValid = ListValidator.ofList(listCartItem);

        return listCartItemValid.ifNotEmpty();
    }

    @Override
    @Modifying
    public void deleteCartItem(int cartItemId) {
         CartItem cartItem = findById(cartItemId);
         this.cartItemRepository.delete(cartItem);
    }

    @Override
    public void updateAlreadyExist(Users users, Books books, CartItem cartItem, int addedQuantity) {
        users.getCartItems().stream()
                .filter(item -> item.getBooks().equals(books))
                .findAny()
                .ifPresent(item ->  updateCartItem(item.getCartItemsID(),cartItem, addedQuantity));
    }
}
