package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.model.entities.CartItem;
import com.example.ecommerce_web.model.entities.OrderItems;
import com.example.ecommerce_web.model.entities.Orders;
import com.example.ecommerce_web.repository.OrderItemsRepository;
import com.example.ecommerce_web.service.OrderItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderItemServiceImpl implements OrderItemService {
    OrderItemsRepository orderItemsRepository;
    ModelMapper modelMapper;

    @Autowired
    public OrderItemServiceImpl(OrderItemsRepository orderItemsRepository, ModelMapper modelMapper){
        this.orderItemsRepository = orderItemsRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderItems createOrderItem(CartItem cartItem) {
        OrderItems orderItems = modelMapper.map(cartItem, OrderItems.class);
        float price = cartItem.getBooks().getBookPrice();
        orderItems.setPrice(price);
        OrderItems savedOrderItem = this.orderItemsRepository.save(orderItems);
        return savedOrderItem;
    }

    @Override
    public void saveOrderItemWith(Orders orders, List<OrderItems> orderItemsList) {
        orderItemsList.stream().forEach(orderItems -> {
            orderItems.setOrders(orders);
            this.orderItemsRepository.save(orderItems);
        });
    }
}
