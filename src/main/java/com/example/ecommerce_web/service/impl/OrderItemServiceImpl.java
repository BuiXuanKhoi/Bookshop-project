package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.repository.OrderItemsRepository;
import com.example.ecommerce_web.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderItemServiceImpl implements OrderItemService {
    OrderItemsRepository orderItemsRepository;

    @Autowired
    public OrderItemServiceImpl(OrderItemsRepository orderItemsRepository){
        this.orderItemsRepository = orderItemsRepository;
    }
}
