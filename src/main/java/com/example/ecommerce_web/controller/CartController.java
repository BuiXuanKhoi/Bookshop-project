package com.example.ecommerce_web.controller;

import com.example.ecommerce_web.model.dto.request.CartItemRequestDTO;
import com.example.ecommerce_web.model.dto.respond.CartItemRespondDTO;
import com.example.ecommerce_web.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    CartItemService cartItemService;

    @Autowired
    public CartController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping
    public CartItemRespondDTO addToCart(@RequestBody CartItemRequestDTO cartItemRequestDTO){
        return this.cartItemService.addToCart(cartItemRequestDTO);
    }

    @GetMapping
    public List<CartItemRespondDTO> getListCartItem(){
        return this.cartItemService.getListCartItem();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCartItem( @PathVariable("id") int id){
        return this.cartItemService.deleteCartItem(id);
    }
}
