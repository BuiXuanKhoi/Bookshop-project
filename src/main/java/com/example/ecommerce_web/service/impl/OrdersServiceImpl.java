package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.repository.OrdersRepository;
import com.example.ecommerce_web.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrdersServiceImpl implements OrdersService {
    OrdersRepository ordersRepository;

    @Autowired
    public OrdersServiceImpl(OrdersRepository ordersRepository){
        this.ordersRepository = ordersRepository;
    }
}
