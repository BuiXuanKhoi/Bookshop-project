package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.mapper.OrderMapper;
import com.example.ecommerce_web.constant.OrderState;
import com.example.ecommerce_web.model.dto.respond.MessageRespond;
import com.example.ecommerce_web.model.dto.respond.OrderRespondDTO;
import com.example.ecommerce_web.model.entities.*;
import com.example.ecommerce_web.repository.CartItemRepository;
import com.example.ecommerce_web.repository.OrderItemsRepository;
import com.example.ecommerce_web.repository.OrdersRepository;
import com.example.ecommerce_web.repository.UserRepository;
import com.example.ecommerce_web.security.service.UserLocal;
import com.example.ecommerce_web.service.OrderItemService;
import com.example.ecommerce_web.service.OrdersService;
import com.example.ecommerce_web.service.UserService;
import com.example.ecommerce_web.validator.ListValidator;
import com.example.ecommerce_web.validator.Validator;
import org.hibernate.criterion.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrdersServiceImpl implements OrdersService {
    OrdersRepository ordersRepository;
    UserLocal userLocal;
    UserRepository userRepository;
    OrderItemsRepository orderItemsRepository;
    CartItemRepository cartItemRepository;
    OrderItemService orderItemService;
    UserService userService;

    @Autowired
    public OrdersServiceImpl(OrdersRepository ordersRepository, UserRepository userRepository,
                             UserLocal userLocal,
                             OrderItemsRepository orderItemsRepository,
                             CartItemRepository cartItemRepository, OrderItemService orderItemService, UserService userService){
        this.ordersRepository = ordersRepository;
        this.userLocal = userLocal;
        this.userRepository = userRepository;
        this.orderItemsRepository = orderItemsRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderItemService = orderItemService;
        this.userService = userService;
    }

    private Orders findById(int id){
        return this.ordersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order Not Found With ID: " + id));
    }

    @Override
    @Transactional
    public Orders add() {
        Users users = userService.findLocalUser();
        List<CartItem> listCarItem = users.getCartItems();

        if (listCarItem.isEmpty()) throw new ResourceNotFoundException("You don't have any cart item to pay ");


        List<OrderItems> listOrderItems = listCarItem.stream()
                                                     .map(cartItem -> orderItemService.add(cartItem))
                                                     .collect(Collectors.toList());
        Orders orders = new Orders();
        orders.setUsers(users);
        orders.setOrderItems(listOrderItems);
        orders.setOrderState(OrderState.PREPARED);
        Orders savedOrder = this.ordersRepository.save(orders);
        orderItemService.saveOrderItemWith(savedOrder, listOrderItems);
        listCarItem.forEach(cartItem -> cartItemRepository.deleteByCartId(cartItem.getCartItemsID()));
        return savedOrder;
    }

    @Override
    public List<Orders> findAllByLocalUser() {
        Users users = userService.findLocalUser();
        List<Orders> listOrder = users.getOrders();
        return ListValidator.ofList(listOrder).ifNotEmpty();
    }

    @Override
    public Orders updateState(int orderId) {
        Orders orders = findById(orderId);
        OrderState orderState = orders.getOrderState();
        OrderState nextOrderState = OrderState.nextOf(orderState);
        orders.setOrderState(nextOrderState);
        return this.ordersRepository.save(orders);
    }
}
