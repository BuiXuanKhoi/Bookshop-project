package com.example.ecommerce_web.controller;

import com.example.ecommerce_web.mapper.CartItemMapper;
import com.example.ecommerce_web.model.dto.request.CartItemRequestDTO;
import com.example.ecommerce_web.model.dto.respond.CartItemRespondDTO;
import com.example.ecommerce_web.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    CartItemService cartItemService;
    CartItemMapper cartItemMapper;

    @Autowired
    public CartController(CartItemService cartItemService, CartItemMapper cartItemMapper) {
        this.cartItemService = cartItemService;
        this.cartItemMapper = cartItemMapper;
    }

    @PostMapping
    public CartItemRespondDTO addToCart(@RequestBody CartItemRequestDTO cartItemRequestDTO){
        return this.cartItemService.addToCart(cartItemRequestDTO);
    }

    @GetMapping
    public List<CartItemRespondDTO> getListCartItem(){
        return this.cartItemService.getListCartItem()
                                   .stream()
                                   .map(cartItemMapper::toDTO)
                                   .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCartItem( @PathVariable("id") int id){
        return this.cartItemService.deleteCartItem(id);
    }
}
