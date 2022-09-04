package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.repository.CartItemRepository;
import com.example.ecommerce_web.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService {
    CartItemRepository cartItemRepository;

    @Autowired
    public CartItemServiceImpl(CartItemRepository cartItemRepository){this.cartItemRepository=cartItemRepository;}
}
