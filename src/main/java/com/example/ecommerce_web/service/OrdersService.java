package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.dto.respond.OrderRespondDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrdersService {

    OrderRespondDTO createOrder();

    List<OrderRespondDTO> getListOrder();

    ResponseEntity<?> updateOrderState(int orderId);


}
