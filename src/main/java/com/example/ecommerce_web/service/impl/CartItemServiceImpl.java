package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.ConstraintViolateException;
import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.mapper.CartItemMapper;
import com.example.ecommerce_web.model.dto.request.CartItemRequestDTO;
import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.model.entities.CartItem;
import com.example.ecommerce_web.model.entities.Users;
import com.example.ecommerce_web.repository.CartItemRepository;
import com.example.ecommerce_web.security.service.UserLocal;
import com.example.ecommerce_web.service.BookService;
import com.example.ecommerce_web.service.CartItemService;
import com.example.ecommerce_web.service.UserService;
import com.example.ecommerce_web.validator.ListValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;


@Service
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final UserLocal userLocal;
    private final CartItemMapper cartItemMapper;
    private final UserService userService;
    private final BookService bookService;

    @Autowired
    public CartItemServiceImpl(CartItemRepository cartItemRepository,
                               UserLocal userLocal,
                               CartItemMapper cartItemMapper, UserService userService,
                               BookService bookService){
        this.cartItemRepository=cartItemRepository;
        this.userLocal = userLocal;
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
        int bookQuantity = bookService.getById(bookId).getQuantity();
        int cartQuantity = cartItemRequestDTO.getQuantity();
        String userName = userLocal.getLocalUserName();
        Users users = userService.findByUserName(userName);
        CartItem cartItem = cartItemMapper.fromDTO(cartItemRequestDTO);
        cartItem.setUsers(users);

        if(isExistCartItem(bookId)) throw new ConstraintViolateException("Cannot Add To Cart Due To Already Existed Cart Item !!!");
        if(bookQuantity < cartQuantity) throw new ConstraintViolateException("Cannot add to cart due to out of stock !!!");

        return this.cartItemRepository.save(cartItem);
    }


    private boolean isExistCartItem(int bookId){
        Books books = bookService.getById(bookId);
        Users users = userService.findLocalUser();
        return this.cartItemRepository.existsByBooksAndUsers(books, users);
    }


    @Override
    public List<CartItem> getAllByLocalUser() {
        String userName = userLocal.getLocalUserName();
        Users users = userService.findByUserName(userName);
        List<CartItem> listCartItem = users.getCartItems();
        ListValidator<CartItem> listCartItemValid = ListValidator.ofList(listCartItem);
        return listCartItemValid.ifNotEmpty();
    }

    @Override
    @Transactional
    public void delete(int cartItemId) {
         this.cartItemRepository.deleteByCartId(cartItemId);
    }


    @Override
    public CartItem update(int cartId, int addedQuantity) {
        CartItem cartItem = findById(cartId);
        int existedQuantity = cartItem.getQuantity();
        int bookQuantity = cartItem.getBooks().getQuantity();
        int cartQuantity = existedQuantity + addedQuantity;

        if(bookQuantity < cartQuantity) throw new ConstraintViolateException("Cannot update cart due to out of stock!!!");
        cartItem.setQuantity(cartQuantity);
        return this.cartItemRepository.save(cartItem);
    }
    @Override
    public CartItem change(int cartId, int decreaseQuantity){
        CartItem cartItem = findById(cartId);
        cartItem.setQuantity(decreaseQuantity);
        return this.cartItemRepository.save(cartItem);
    }
}
