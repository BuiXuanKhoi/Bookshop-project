package com.example.ecommerce_web.service;

import com.example.ecommerce_web.constant.BookState;
import com.example.ecommerce_web.exceptions.ConstraintViolateException;
import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.mapper.CartItemMapper;
import com.example.ecommerce_web.model.dto.request.CartItemRequestDTO;
import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.model.entities.CartItem;
import com.example.ecommerce_web.model.entities.Users;
import com.example.ecommerce_web.repository.CartItemRepository;
import com.example.ecommerce_web.security.service.UserLocal;
import com.example.ecommerce_web.service.impl.CartItemServiceImpl;
import org.apache.catalina.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.configuration.IMockitoConfiguration;

import javax.swing.text.html.Option;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartItemServiceTest {
    CartItemRepository cartItemRepository;
    UserLocal userLocal;
    CartItemMapper cartItemMapper;
    UserService userService;
    BookService bookService;
    CartItemServiceImpl cartItemServiceImpl;

    @BeforeEach
    void init() {
        cartItemMapper = mock(CartItemMapper.class);
        userLocal = mock(UserLocal.class);
        userService = mock(UserService.class);
        bookService = mock(BookService.class);
        cartItemRepository = mock(CartItemRepository.class);
        cartItemServiceImpl = new CartItemServiceImpl(cartItemRepository, userLocal, cartItemMapper, userService, bookService);
    }

    @Test
    void whenFindCartItemByID_throwResourceNotFoundException_ifIdNoExist () {
            int id = 6;
            when(cartItemRepository.findById(id)).thenReturn(Optional.empty());
            ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> cartItemServiceImpl.findById(id));
            assertThat(exception.getMessage(),is("Not Found Cart Item With ID: " + id));
    }

    @Test
    void whenAddCartItem_thenReturnNewCartItem () {
        CartItemRequestDTO cartItemRequestDTO = mock(CartItemRequestDTO.class);
        int bookQuantity = 0;
        int bookId = 0;
        int cartQuantity = 0;
        Books books = mock(Books.class);
        String userName = "Tuan";
        Users users =  mock(Users.class);
        CartItem cartItem = mock(CartItem.class);
        CartItem newCartItem = mock(CartItem.class);

        when(cartItemRequestDTO.getBookId()).thenReturn(bookId);
        when(userLocal.getLocalUserName()).thenReturn(userName);
        when(userService.findByUserName(userName)).thenReturn(users);
        when(bookService.getById(bookId)).thenReturn(books);
        when(books.getQuantity()).thenReturn(bookQuantity);
        when(cartItemRequestDTO.getQuantity()).thenReturn(cartQuantity);
        when(cartItemMapper.fromDTO(cartItemRequestDTO)).thenReturn(cartItem);
        cartItem.setUsers(users);
        verify(cartItem).setUsers(users);

        when(cartItemRepository.save(cartItem)).thenReturn(newCartItem);
        assertThat(cartItemServiceImpl.add(cartItemRequestDTO),is(newCartItem));
    }

    @Test
    void whenAddNewCartItem_throwConstrainViolateException_ifBookIdHadAlreadyExisted () {
        CartItemRequestDTO cartItemRequestDTO = mock(CartItemRequestDTO.class);
        int bookId = 3;
        Users users = mock(Users.class);
        Books books = mock(Books.class);
        CartItem cartItem = mock(CartItem.class);

        when(cartItemRequestDTO.getBookId()).thenReturn(bookId);
        when(bookService.getById(bookId)).thenReturn(books);

        when(cartItemMapper.fromDTO(cartItemRequestDTO)).thenReturn(cartItem);

        when(userService.findLocalUser()).thenReturn(users);
        when(cartItemRepository.existsByBooksAndUsers(books,users)).thenReturn(true);

        ConstraintViolateException constrain = Assertions.assertThrows(ConstraintViolateException.class,
                ()-> cartItemServiceImpl.add(cartItemRequestDTO));
        assertThat(constrain.getMessage(),is("Cannot Add To Cart Due To Already Existed Cart Item !!!"));
    }

    @Test
    void whenNewCartItem_throwConstrainViolateException_ifBookOutOfStock () {
        int bookId = 3;
        int bookQuantity = 3;
        int cartQuantity = 4;
        CartItem cartItem = mock(CartItem.class);
        String userName = "Tuan";
        Users users = mock(Users.class);
        Books books = mock(Books.class);
        CartItemRequestDTO cartItemRequestDTO = mock(CartItemRequestDTO.class);

        when(userLocal.getLocalUserName()).thenReturn(userName);
        when(userService.findByUserName(userName)).thenReturn(users);

        when(cartItemMapper.fromDTO(cartItemRequestDTO)).thenReturn(cartItem);
        when(cartItemRequestDTO.getQuantity()).thenReturn(cartQuantity);

        when(cartItemRequestDTO.getBookId()).thenReturn(bookId);
        when(bookService.getById(bookId)).thenReturn(books);
        when(books.getQuantity()).thenReturn(bookQuantity);

        ConstraintViolateException constrain = Assertions.assertThrows(ConstraintViolateException.class,
                ()-> cartItemServiceImpl.add(cartItemRequestDTO));

        assertThat(constrain.getMessage(),is("Cannot add to cart due to out of stock !!!"));
    }

    @Test
    void whenGetListCartItem_returnListCart () {
        String userName = "Tuan";
        Users users = mock(Users.class);
        List<CartItem> listCartItem = mock(List.class);

        when(userLocal.getLocalUserName()).thenReturn(userName);
        when(userService.findByUserName(userName)).thenReturn(users);
        when(users.getCartItems()).thenReturn(listCartItem);

        assertThat(cartItemServiceImpl.getAllByLocalUser(),is(listCartItem));
    }

    @Test
    void whenGetListCartItem_throwResourceNotFoundException_ifListIsEmpty(){
        String userName = "Tuan";
        Users users = mock(Users.class);
        List<CartItem> cartItemList = new ArrayList<>();

        when(userLocal.getLocalUserName()).thenReturn(userName);
        when(userService.findByUserName(userName)).thenReturn(users);
        when(users.getCartItems()).thenReturn(cartItemList);

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                ()-> cartItemServiceImpl.getAllByLocalUser());
        assertThat(exception.getMessage(),is("List is Empty !!!"));
    }

    @Test
    void whenDeleteCartItem_thenThrowResourceNotFoundException_ifCartItemNotExist(){
        int cartItemId = 3;
        when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> cartItemServiceImpl.delete(cartItemId));

        assertThat(exception.getMessage(), is("Not Found Cart Item With ID: " + cartItemId));
    }

    @Test
    void whenDeleteCartItem_returnCartItemHasBeenDeleted (){
        int cartItemId = 3;
        CartItem cartItem = mock(CartItem.class);

        when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.of(cartItem));
        cartItemRepository.deleteByCartId(cartItemId);
        verify(cartItemRepository).deleteByCartId(cartItemId);
    }

    @Test
    void whenUpdateCartItem_returnNewCartItem (){
        int cartId = 10;
        int addedQuantity = 4;
        CartItem cartItem = mock(CartItem.class);
        Books books = mock(Books.class);
        int bookQuantity = 24;
        int existedQuantity = 3;
        CartItem savedCartItem = mock(CartItem.class);
        int cartQuantity = 14;

        when(cartItemRepository.findById(cartId)).thenReturn(Optional.of(cartItem));
        when(cartItem.getQuantity()).thenReturn(existedQuantity);
        when(cartItem.getBooks()).thenReturn(books);
        when(books.getQuantity()).thenReturn(bookQuantity);


        cartItem.setQuantity(cartQuantity);
        verify(cartItem, times(1)).setQuantity(cartQuantity);

        when(cartItemRepository.save(cartItem)).thenReturn(savedCartItem);
        assertThat(cartItemServiceImpl.update(cartId, addedQuantity), is(savedCartItem));
    }

    @Test
    void whenUpdateCartItem_throwConstraintViolateException_ifBooksOutOfStock (){
        int cartId = 3;
        int addedQuantity = 4;
        CartItem cartItem = mock(CartItem.class);
        Books books = mock(Books.class);
        int bookQuantity = 4;
        int existedQuantity = 3;
        int cartQuantity = bookQuantity+existedQuantity;

        when(cartItemRepository.findById(cartId)).thenReturn(Optional.of(cartItem));
        when(cartItem.getQuantity()).thenReturn(existedQuantity);
        when(cartItem.getBooks()).thenReturn(books);
        when(books.getQuantity()).thenReturn(bookQuantity);

        ConstraintViolateException constraint = Assertions.assertThrows(ConstraintViolateException.class,
                ()->cartItemServiceImpl.update(cartId,addedQuantity));
        assertThat(constraint.getMessage(),is("Cannot update cart due to out of stock!!!"));
    }

    @Test
    void whenUpdateCartItem_throwResourceNotFoundException_ifCartNotExist(){
        int cartId = 1000;
        int addedQuantity = 100;
        when(cartItemRepository.findById(cartId)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> cartItemServiceImpl.update(cartId, addedQuantity));

        assertThat(exception.getMessage(), is("Not Found Cart Item With ID: " + cartId));

    }

    @Test
    void whenChangeCartItemQuantity_returnCartItemAfterChange () {
        int cartId = 3;
        int decreaseQuantity = 2;
        CartItem cartItem = mock(CartItem.class);
        CartItem savedCartItem = mock(CartItem.class);

        when(cartItemRepository.findById(cartId)).thenReturn(Optional.of(cartItem));

        cartItem.setQuantity(decreaseQuantity);
        verify(cartItem).setQuantity(decreaseQuantity);
        when(cartItemRepository.save(cartItem)).thenReturn(savedCartItem);
        assertThat(cartItemServiceImpl.change(cartId, decreaseQuantity), is(savedCartItem));

    }

    @Test
    void whenChangeQuantityCartItemQuantity_thenThrowResourceNotFoundException_ifCartItemNotExist(){
        int cartId = 10000;
        int changeQuantity = 100;

        when(cartItemRepository.findById(cartId)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> cartItemServiceImpl.change(cartId, changeQuantity));

        assertThat(exception.getMessage(), is("Not Found Cart Item With ID: " + cartId));
    }

}