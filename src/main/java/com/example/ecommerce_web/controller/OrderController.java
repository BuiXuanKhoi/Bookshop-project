package com.example.ecommerce_web.controller;

import com.example.ecommerce_web.mapper.OrderMapper;
import com.example.ecommerce_web.model.dto.respond.OrderRespondDTO;
import com.example.ecommerce_web.model.entities.Orders;
import com.example.ecommerce_web.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    OrdersService ordersService;
    OrderMapper orderMapper;

    @Autowired
    public OrderController(OrdersService ordersService, OrderMapper orderMapper) {
        this.ordersService = ordersService;
        this.orderMapper = orderMapper;
    }

    @PostMapping
    public OrderRespondDTO addOrders(){
        Orders orders =  this.ordersService.createOrder();
        return orderMapper.toDTO(orders);
    }

    @GetMapping
    public List<OrderRespondDTO> getListOrder(){
        return this.ordersService.getListOrder()
                                 .stream()
                                 .map(orderMapper::toDTO)
                                 .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public OrderRespondDTO updateOrderState(@PathVariable int id){
        Orders orders =  this.ordersService.updateOrderState(id);
        return orderMapper.toDTO(orders);
    }
}
