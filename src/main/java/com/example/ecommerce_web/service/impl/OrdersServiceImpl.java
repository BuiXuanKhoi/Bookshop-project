package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.OrderState;
import com.example.ecommerce_web.model.dto.respond.CartItemRespondDTO;
import com.example.ecommerce_web.model.dto.respond.MessageRespond;
import com.example.ecommerce_web.model.dto.respond.OrderItemRespondDTO;
import com.example.ecommerce_web.model.dto.respond.OrderRespondDTO;
import com.example.ecommerce_web.model.entities.CartItem;
import com.example.ecommerce_web.model.entities.OrderItems;
import com.example.ecommerce_web.model.entities.Orders;
import com.example.ecommerce_web.model.entities.Users;
import com.example.ecommerce_web.repository.CartItemRepository;
import com.example.ecommerce_web.repository.OrderItemsRepository;
import com.example.ecommerce_web.repository.OrdersRepository;
import com.example.ecommerce_web.repository.UserRepository;
import com.example.ecommerce_web.security.service.UserLocal;
import com.example.ecommerce_web.service.OrderItemService;
import com.example.ecommerce_web.service.OrdersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class OrdersServiceImpl implements OrdersService {
    OrdersRepository ordersRepository;
    UserLocal userLocal;
    UserRepository userRepository;
    ModelMapper modelMapper;
    OrderItemsRepository orderItemsRepository;
    CartItemRepository cartItemRepository;
    OrderItemService orderItemService;

    @Autowired
    public OrdersServiceImpl(OrdersRepository ordersRepository, UserRepository userRepository,
                             UserLocal userLocal, ModelMapper modelMapper,
                             OrderItemsRepository orderItemsRepository,
                             CartItemRepository cartItemRepository, OrderItemService orderItemService){
        this.ordersRepository = ordersRepository;
        this.userLocal = userLocal;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.orderItemsRepository = orderItemsRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderItemService = orderItemService;
    }

    @Override
    public OrderRespondDTO createOrder() {
        String userName = userLocal.getLocalUserName();
        Users users = this.userRepository.findUserByUserName(userName).get();
        List<CartItem> listCarItem = users.getCartItems();

        if (listCarItem.isEmpty()){
            throw new ResourceNotFoundException("You don't have any cart item to pay ");
        }

        List<OrderItems> listOrderItems = transferFromCart(listCarItem);
        Orders orders = new Orders();

        orders.setUsers(users);
        orders.setOrderItems(listOrderItems);
        orders.setOrderState(OrderState.PREPARED);
        Orders savedOrder = this.ordersRepository.save(orders);

        this.orderItemService.saveOrderItemWith(savedOrder, listOrderItems);
        this.cartItemRepository.deleteCartItemByUsers(users.getUserId());


        OrderRespondDTO orderRespondDTO = new OrderRespondDTO(orders);


        return orderRespondDTO;
    }

    @Override
    public List<OrderRespondDTO> getListOrder() {
        String userName = userLocal.getLocalUserName();
        Optional<Users> usersOptional = this.userRepository.findUserByUserName(userName);

        List<Orders> listOrder = usersOptional.map(Users::getOrders).get();

        if(listOrder.isEmpty()){
            throw new ResourceNotFoundException("You don't have any orders !!!");
        }

        List<OrderRespondDTO> listOrderRespond = listOrder.stream()
                                                          .map(OrderRespondDTO::new)
                                                          .collect(Collectors.toList());

        return listOrderRespond;
    }

    @Override
    public ResponseEntity<?> updateOrderState(int orderId) {
        Optional<Orders> ordersOptional = this.ordersRepository.findById(orderId);

        ordersOptional.orElseThrow(
                () -> new ResourceNotFoundException("Order Not Found With ID: " + orderId)
        );

        Orders orders = ordersOptional.get();

        OrderState orderState = orders.getOrderState();

        switch (orderState)
        {
            case PREPARED -> orders.setOrderState(OrderState.PACKAGED);
            case PACKAGED -> orders.setOrderState(OrderState.DELIVERED);
            case DELIVERED -> orders.setOrderState(OrderState.RECEIVED);
            case RECEIVED -> orders.setOrderState(OrderState.COMPLETED);
            case COMPLETED -> throw new ResourceNotFoundException("Completed is the final state !!!");
            default -> throw new ResourceNotFoundException("State Not Available !!!");
        }

        this.ordersRepository.save(orders);

        MessageRespond messageRespond = new MessageRespond(HttpStatus.OK.value(), "Order State update successfully !!!");
        return ResponseEntity.ok(messageRespond);
    }

    public List<OrderItems> transferFromCart(List<CartItem> listCartItem){

        List<OrderItems> listOrderItems = new ArrayList<>();

        for(CartItem cartItem : listCartItem)
        {
            OrderItems orderItems = this.orderItemService.createOrderItem(cartItem);
            listOrderItems.add(orderItems);
        }

        return listOrderItems;
    }
}
