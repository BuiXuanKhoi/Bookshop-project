package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.dto.request.AddCartItemRequest;
import com.example.ecommerce_web.model.dto.respond.CartItemRespondDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartItemService {

    CartItemRespondDTO addToCart(AddCartItemRequest addCartItemRequest);

    List<CartItemRespondDTO> getListCartItem();

    ResponseEntity<?> deleteCartItem(int cartItemId);





}
