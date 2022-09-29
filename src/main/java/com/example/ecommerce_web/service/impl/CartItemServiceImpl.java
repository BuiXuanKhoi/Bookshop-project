package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.ConstraintViolateException;
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
    UserRepository userRepository;
    CartItemMapper cartItemMapper;
    UserService userService;
    BookService bookService;

    @Autowired
    public CartItemServiceImpl(CartItemRepository cartItemRepository, BookRepository bookRepository,
                               UserLocal userLocal, UserRepository userRepository,
                               CartItemMapper cartItemMapper, UserService userService,
                               BookService bookService){
        this.cartItemRepository=cartItemRepository;
        this.bookRepository = bookRepository;
        this.userLocal = userLocal;
        this.userRepository = userRepository;
        this.cartItemMapper = cartItemMapper;
        this.userService = userService;
        this.bookService = bookService;
    }

    private CartItem findById(int id){
        return this.cartItemRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not Found Cart Item With ID: " + id));
    }

    @Override
    public CartItem add(CartItemRequestDTO cartItemRequestDTO) {
        int bookId = cartItemRequestDTO.getBookId();
        String userName = userLocal.getLocalUserName();
        Users users = userService.findByUserName(userName);
        CartItem cartItem = cartItemMapper.fromDTO(cartItemRequestDTO);
        cartItem.setUsers(users);

        if(isExistCartItem(bookId)){
            throw new ConstraintViolateException("Cannot Add To Cart Due To Already Existed Cart Item !!!");
        }
        return this.cartItemRepository.save(cartItem);
    }


    private boolean isExistCartItem(int bookId){
        try
        {
            CartItem cartItem = bookService.getCartItemByBook(bookId);
            return true;
        }
        catch (ResourceNotFoundException ex)
        {
            return false;
        }
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
    public CartItem update(int cartId, int addedQuantity) {
        CartItem cartItem = findById(cartId);
        int existedQuantity = cartItem.getQuantity();
        cartItem.setQuantity(existedQuantity + addedQuantity);
        return this.cartItemRepository.save(cartItem);
    }
}
