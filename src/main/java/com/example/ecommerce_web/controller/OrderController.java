package com.example.ecommerce_web.controller;

import com.example.ecommerce_web.model.dto.respond.OrderRespondDTO;
import com.example.ecommerce_web.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    OrdersService ordersService;

    @Autowired
    public OrderController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping
    public OrderRespondDTO addOrders(){
        return this.ordersService.createOrder();
    }

    @GetMapping
    public List<OrderRespondDTO> getListOrder(){
        return this.ordersService.getListOrder();
    }
}
