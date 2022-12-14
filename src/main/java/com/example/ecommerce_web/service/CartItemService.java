package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.dto.request.CartItemRequestDTO;
import com.example.ecommerce_web.model.dto.respond.CartItemRespondDTO;
import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.model.entities.CartItem;
import com.example.ecommerce_web.model.entities.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartItemService {
    CartItem add(CartItemRequestDTO cartItemRequestDTO);
    List<CartItem> getAllByLocalUser();
    void delete(int cartItemId);
    CartItem update(int cartId, int addedQuantity);
    CartItem change(int cartId, int decreaseQuantity);
    CartItem findById(int id);
}
