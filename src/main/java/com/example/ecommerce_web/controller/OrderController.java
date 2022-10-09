package com.example.ecommerce_web.controller;

import com.example.ecommerce_web.mapper.OrderMapper;
import com.example.ecommerce_web.model.dto.respond.OrderRespondDTO;
import com.example.ecommerce_web.model.dto.respond.PageManageOrder;
import com.example.ecommerce_web.model.entities.Orders;
import com.example.ecommerce_web.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(maxAge = 3600, origins = "*")
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
        Orders orders =  this.ordersService.add();
        return orderMapper.toDTO(orders);
    }

    @GetMapping
    public List<OrderRespondDTO> getListOrder(){
        return this.ordersService.findAllByLocalUser()
                                 .stream()
                                 .sorted(Comparator.comparing(Orders::getOrderId))
                                 .map(orderMapper::toDTO)
                                 .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public OrderRespondDTO updateOrderState(@PathVariable int id){
        Orders orders =  this.ordersService.updateState(id);
        return orderMapper.toDTO(orders);
    }

    @GetMapping("/manage")
    public PageManageOrder getPageManageOrder(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "search", defaultValue = "%", required = false) String search
    ){
        return this.ordersService.getPageForManage(page, search);
    }
}
