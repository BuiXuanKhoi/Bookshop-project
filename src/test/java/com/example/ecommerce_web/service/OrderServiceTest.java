package com.example.ecommerce_web.service;

import com.example.ecommerce_web.constant.OrderState;
import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.mapper.OrderItemMapper;
import com.example.ecommerce_web.model.entities.CartItem;
import com.example.ecommerce_web.model.entities.OrderItems;
import com.example.ecommerce_web.model.entities.Orders;
import com.example.ecommerce_web.model.entities.Users;
import com.example.ecommerce_web.repository.CartItemRepository;
import com.example.ecommerce_web.repository.OrdersRepository;
import com.example.ecommerce_web.service.impl.OrdersServiceImpl;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    OrdersRepository ordersRepository;
    CartItemRepository cartItemRepository;
    OrderItemService orderItemService;
    UserService userService;
    OrderItemMapper orderItemMapper;
    OrdersServiceImpl ordersService;

    @BeforeEach
    void initTest(){
        orderItemMapper = mock(OrderItemMapper.class);
        ordersRepository = mock(OrdersRepository.class);
        orderItemService = mock(OrderItemService.class);
        userService = mock(UserService.class);
        cartItemRepository = mock(CartItemRepository.class);
        ordersService = new OrdersServiceImpl(ordersRepository, cartItemRepository, orderItemService, userService, orderItemMapper);
    }


    @Test
    void whenFindById_shouldThrowResoureNotFoundException_whenIdNotExist(){
        when(ordersRepository.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> ordersService.findById(1));
        assertThat(exception.getMessage(), is("Order Not Found With ID: 1"));
    }

    @Test
    void whenFindById_shouldReturnOrder_whenIdExisted(){
        Orders orders = mock(Orders.class);
        when(ordersRepository.findById(1)).thenReturn(Optional.of(orders));
        assertThat(ordersService.findById(1), is(orders));
    }

    @Test
    void whenFindAllByLocalUser_shouldThrowResourceNotFoundException_ifListEmpty(){
        Users users = mock(Users.class);
        List<Orders> ordersList = new ArrayList<>();

        when(userService.findLocalUser()).thenReturn(users);
        when(users.getOrders()).thenReturn(ordersList);

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> ordersService.findAllByLocalUser());

        assertThat(exception.getMessage(), is("List is Empty !!!"));
    }

    @Test
    void whenFindAllByLocalUser_thenReturnOrders_ifListNotEmpty(){
        Users users = mock(Users.class);
        List<Orders> ordersList = List.of(
                Orders.builder()
                      .orderState(OrderState.RECEIVED)
                      .orderId(1)
                      .orderItems(null)
                      .users(users)
                      .createDate(new Date())
                      .updateDate(new Date())
                      .updateBy(users).build(),
                Orders.builder()
                        .orderState(OrderState.RECEIVED)
                        .orderId(2)
                        .orderItems(null)
                        .users(users)
                        .createDate(new Date())
                        .updateDate(new Date())
                        .updateBy(users).build()
        );
        when(userService.findLocalUser()).thenReturn(users);
        when(users.getOrders()).thenReturn(ordersList);

        assertThat(ordersService.findAllByLocalUser(), is(ordersList));
    }

    @Test
    void whenAddOrder_thenThrowResourceNotFoundException_ifDontHaveAnyCartItem(){
        Users users = mock(Users.class);
        List<CartItem> cartItemList = new ArrayList<>();

        when(userService.findLocalUser()).thenReturn(users);
        when(users.getCartItems()).thenReturn(cartItemList);

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> ordersService.add());
        assertThat(exception.getMessage(), is("You don't have any cart item to pay "));
    }

//    @Test
//    void whenAddOrder_thenReturnOrders_ifHaveCartItemExisted(){
//        Users users = mock(Users.class);
//        List<CartItem> cartItemList = List.of(
//                CartItem.builder()
//                        .users(users)
//                        .cartItemsID(1)
//                        .books(null)
//                        .quantity(10)
//                        .build(),
//                CartItem.builder()
//                        .users(users)
//                        .cartItemsID(2)
//                        .quantity(20)
//                        .build()
//        );
//        Stream<CartItem> streamCartItem = Stream.of(
//                CartItem.builder()
//                        .users(users)
//                        .cartItemsID(1)
//                        .books(null)
//                        .quantity(10)
//                        .build(),
//                CartItem.builder()
//                        .users(users)
//                        .cartItemsID(2)
//                        .quantity(20)
//                        .build()
//        );
//
//        Stream<OrderItems> orderItemsStream = Stream.of(
//                OrderItems.builder()
//                          .orders(null)
//                          .orderItemsID(1)
//                          .quantity(10)
//                          .books(null)
//                          .price(3.0f)
//                          .build(),
//                OrderItems.builder()
//                        .orders(null)
//                        .orderItemsID(2)
//                        .quantity(10)
//                        .books(null)
//                        .price(3.0f)
//                        .build()
//        );
//
//        List<OrderItems> orderItemsList = List.of(
//                OrderItems.builder()
//                        .orders(null)
//                        .orderItemsID(1)
//                        .quantity(10)
//                        .books(null)
//                        .price(3.0f)
//                        .build(),
//                OrderItems.builder()
//                        .orders(null)
//                        .orderItemsID(2)
//                        .quantity(10)
//                        .books(null)
//                        .price(3.0f)
//                        .build()
//        );
//
//        when(userService.findLocalUser()).thenReturn(users);
//        when(users.getCartItems()).thenReturn(cartItemList);
//        when(cartItemList.stream()).thenReturn(streamCartItem);
//        when(streamCartItem.map(orderItemService::add)).thenReturn(orderItemsStream);
//        when(orderItemsStream.collect(Collectors.toList())).thenReturn(orderItemsList);
//
//        Orders orders = mock(Orders.class);
//        Orders savedOrders = mock(Orders.class);
//
//        orders.setUsers(users);
//        orders.setOrderItems(orderItemsList);
//        orders.setOrderState(OrderState.PREPARED);
//        orderItemService.saveOrderItemWith(savedOrders, orderItemsList);
//        cartItemList.forEach(cartItem -> cartItemRepository.deleteByCartId(cartItem.getCartItemsID()));
//
//        verify(orders).setUsers(users);
//        verify(orders).setOrderItems(orderItemsList);
//        verify(orders).setOrderState(OrderState.PREPARED);
//
//        when(ordersRepository.save(orders)).thenReturn(savedOrders);
//        verify(orderItemService).saveOrderItemWith(savedOrders, orderItemsList);
//        verify(cartItemList).forEach(cartItem -> cartItemRepository.deleteByCartId(cartItem.getCartItemsID()));
//
//        assertThat(ordersService.add(), is(savedOrders));
//    }
}
