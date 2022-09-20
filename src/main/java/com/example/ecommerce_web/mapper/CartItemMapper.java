package com.example.ecommerce_web.mapper;

import com.example.ecommerce_web.model.dto.request.CartItemRequestDTO;
import com.example.ecommerce_web.model.dto.respond.CartItemRespondDTO;
import com.example.ecommerce_web.model.entities.CartItem;

public interface CartItemMapper extends RequestMapper<CartItemRequestDTO, CartItem>,
        RespondMapper<CartItem, CartItemRespondDTO> {
}
