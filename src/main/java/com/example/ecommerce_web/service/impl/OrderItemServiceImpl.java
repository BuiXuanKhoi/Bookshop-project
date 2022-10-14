package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.model.entities.CartItem;
import com.example.ecommerce_web.model.entities.OrderItems;
import com.example.ecommerce_web.model.entities.Orders;
import com.example.ecommerce_web.repository.OrderItemsRepository;
import com.example.ecommerce_web.service.BookService;
import com.example.ecommerce_web.service.OrderItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemsRepository orderItemsRepository;
    private final ModelMapper modelMapper;
    private final BookService bookService;

    @Autowired
    public OrderItemServiceImpl(OrderItemsRepository orderItemsRepository, ModelMapper modelMapper, BookService bookService){
        this.orderItemsRepository = orderItemsRepository;
        this.modelMapper = modelMapper;
        this.bookService = bookService;
    }

    @Override
    public OrderItems getById(int id) {
        return this.orderItemsRepository.findById(id)
                                        .orElseThrow(
                                                () -> new ResourceNotFoundException("Not Found Order Item With ID: " + id)
                                        );
    }

    @Override
    public OrderItems add(CartItem cartItem) {
        OrderItems orderItems = modelMapper.map(cartItem, OrderItems.class);
        float price = cartItem.getBooks().getBookPrice();
        Books books = cartItem.getBooks();
        int quantity = cartItem.getQuantity();
        orderItems.setPrice(price);
        OrderItems savedOrderItem = this.orderItemsRepository.save(orderItems);
        bookService.checkout(books.getBookId(), quantity);
        return savedOrderItem;
    }

    @Override
    public void saveOrderItemWith(Orders orders, List<OrderItems> orderItemsList) {
        orderItemsList.forEach(orderItems -> {
            orderItems.setOrders(orders);
            this.orderItemsRepository.save(orderItems);
        });
    }
}
