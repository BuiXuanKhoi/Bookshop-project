package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
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
import com.example.ecommerce_web.service.CartItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    @Autowired
    public CartItemServiceImpl(CartItemRepository cartItemRepository, BookRepository bookRepository,
                               UserLocal userLocal, ModelMapper modelMapper, UserRepository userRepository){
        this.cartItemRepository=cartItemRepository;
        this.bookRepository = bookRepository;
        this.userLocal = userLocal;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public CartItemRespondDTO addToCart(CartItemRequestDTO cartItemRequestDTO) {
        int bookId = cartItemRequestDTO.getBookId();
        String userName = userLocal.getLocalUserName();

        CartItem cartItem = modelMapper.map(cartItemRequestDTO, CartItem.class);
        Users users = this.userRepository.findUserByUserName(userName).get();
        Optional<Books> booksOptional = this.bookRepository.findById(bookId);

        booksOptional.orElseThrow(
                () -> new ResourceNotFoundException("Not Found Book With ID: " + bookId)
        );

        Optional<CartItem> existedCartItem = this.cartItemRepository
                .getCartItemByBooksAndUsers(users.getUserId(), bookId);

        existedCartItem.ifPresent(existedItem ->{
                    int existedQuantity = existedCartItem.get().getQuantity();
                    int addedQuantity = cartItemRequestDTO.getQuantity();
                    int existedCartId = existedCartItem.get().getCartItemsID();
                    cartItem.setQuantity(existedQuantity + addedQuantity);
                    cartItem.setCartItemsID(existedCartId);
                }
        );


        Books books = booksOptional.get();

        cartItem.setBooks(books);
        cartItem.setUsers(users);

        CartItem savedCartItem = this.cartItemRepository.save(cartItem);

        return new CartItemRespondDTO(savedCartItem);
    }

    @Override
    public List<CartItemRespondDTO> getListCartItem() {
        String userName = userLocal.getLocalUserName();
        Optional<Users> usersOptional = this.userRepository.findUserByUserName(userName);

        List<CartItem> listCartOptional = usersOptional.map(Users::getCartItems).get();

        if(listCartOptional.isEmpty()){
            throw new ResourceNotFoundException("You don't have any item in your carts !!!");
        }

        List<CartItemRespondDTO> listCartRespond = listCartOptional.stream()
                                                                    .map(CartItemRespondDTO::new)
                                                                    .collect(Collectors.toList());

        return listCartRespond;
    }

    @Override
    @Modifying
    public ResponseEntity<?> deleteCartItem(int cartItemId) {

        this.cartItemRepository.deleteById(cartItemId);
        MessageRespond messageRespond = new MessageRespond(HttpStatus.OK.value(), "Delete Cart Item Successfully !!!");
        return ResponseEntity.ok(messageRespond);
    }
}
