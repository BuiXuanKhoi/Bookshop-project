package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.constant.OrderState;
import com.example.ecommerce_web.mapper.OrderItemMapper;
import com.example.ecommerce_web.model.alter.ManageOrderRespondDTO;
import com.example.ecommerce_web.model.dto.respond.OrderItemRespondDTO;
import com.example.ecommerce_web.model.dto.respond.PageManageOrder;
import com.example.ecommerce_web.model.entities.*;
import com.example.ecommerce_web.repository.CartItemRepository;
import com.example.ecommerce_web.repository.OrdersRepository;
import com.example.ecommerce_web.service.OrderItemService;
import com.example.ecommerce_web.service.OrdersService;
import com.example.ecommerce_web.service.UserService;
import com.example.ecommerce_web.validator.ListValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class OrdersServiceImpl implements OrdersService {
    private final OrdersRepository ordersRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemService orderItemService;
    private final UserService userService;
    private final OrderItemMapper orderItemMapper;

    @Autowired
    public OrdersServiceImpl(OrdersRepository ordersRepository,
                             CartItemRepository cartItemRepository, OrderItemService orderItemService,
                             UserService userService, OrderItemMapper orderItemMapper){
        this.ordersRepository = ordersRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderItemService = orderItemService;
        this.userService = userService;
        this.orderItemMapper = orderItemMapper;
    }

    @Override
    public Orders findById(int id){
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
                                                     .map(orderItemService::add)
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
    public PageManageOrder getPageForManage(int page, String search) {

        Pageable pageable = PageRequest.of(page, 10);
        search = search.toLowerCase();
        Page<ManageOrderRespondDTO> manageOrderPage = ordersRepository.getPageManageOrders(pageable, search);
        List<ManageOrderRespondDTO> listManageOrder = manageOrderPage.getContent();


        return PageManageOrder.builder()
                              .listManageOrder(
                                      listManageOrder.stream()
                                                     .map(this::updatePriceAndOrderItems)
                                                     .collect(Collectors.toList())
                              )
                              .pageSize(10)
                              .currentPage(page)
                              .totalElements(manageOrderPage.getTotalElements())
                              .build();
    }

    //

    @Override
    public Orders updateState(int orderId) {
        Orders orders = findById(orderId);
        Users admin = userService.findLocalUser();
        OrderState orderState = orders.getOrderState();
        OrderState nextOrderState = OrderState.nextOf(orderState);
        orders.setOrderState(nextOrderState);
        orders.setUpdateBy(admin);
        return this.ordersRepository.save(orders);
    }

    private ManageOrderRespondDTO updatePriceAndOrderItems(ManageOrderRespondDTO manageOrder) {
        Orders order = findById(manageOrder.getOrderId());
        float totalPrice = order.getOrderItems().stream()
                                                .map(orderItems -> orderItems.getPrice() * orderItems.getQuantity())
                                                .reduce(0.0f, Float::sum);

        List<OrderItemRespondDTO> orderItems = order.getOrderItems().stream()
                                                                    .map(orderItemMapper::toDTO)
                                                                    .collect(Collectors.toList());
        manageOrder.setTotalPrice(totalPrice);
        manageOrder.setOrderItems(orderItems);
        return manageOrder;
    }
}
