package com.example.ecommerce_web.controller;

import com.example.ecommerce_web.mapper.CartItemMapper;
import com.example.ecommerce_web.model.dto.request.CartItemRequestDTO;
import com.example.ecommerce_web.model.dto.respond.CartItemRespondDTO;
import com.example.ecommerce_web.model.dto.respond.MessageRespond;
import com.example.ecommerce_web.model.entities.CartItem;
import com.example.ecommerce_web.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/carts")
@CrossOrigin(maxAge = 3600, origins = "*")
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
        CartItem cartItem = this.cartItemService.add(cartItemRequestDTO);
        return cartItemMapper.toDTO(cartItem);
    }

    @GetMapping
    public List<CartItemRespondDTO> getListCartItem(){
        return this.cartItemService.getAllByLocalUser()
                                   .stream()
                                   .map(cartItemMapper::toDTO)
                                   .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public CartItemRespondDTO updateCart(@PathVariable int id,
                                         @RequestParam(name = "quantity", defaultValue = "1")String addedQuantity){
        int quantityConverted = Integer.parseInt(addedQuantity);
        CartItem cartItem = this.cartItemService.update(id, quantityConverted);
        return cartItemMapper.toDTO(cartItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCartItem( @PathVariable("id") int id){
        this.cartItemService.delete(id);
        MessageRespond messageRespond = new MessageRespond(HttpStatus.OK.value(), "Delete Cart Item Successfully !!!");
        return ResponseEntity.ok(messageRespond);
    }
}
