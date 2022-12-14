package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.entities.CartItem;
import com.example.ecommerce_web.model.entities.OrderItems;
import com.example.ecommerce_web.model.entities.Orders;

import java.util.List;

public interface OrderItemService {
    OrderItems getById(int id);
    OrderItems add(CartItem cartItem);
    void saveOrderItemWith(Orders orders, List<OrderItems> orderItemsList);
}
