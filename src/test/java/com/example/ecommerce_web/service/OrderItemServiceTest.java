package com.example.ecommerce_web.service;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.model.entities.CartItem;
import com.example.ecommerce_web.model.entities.OrderItems;
import com.example.ecommerce_web.model.entities.Orders;
import com.example.ecommerce_web.repository.OrderItemsRepository;
import com.example.ecommerce_web.service.impl.OrderItemServiceImpl;
import org.modelmapper.ModelMapper;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Optional;

public class OrderItemServiceTest {

     OrderItemsRepository orderItemsRepository;
     ModelMapper modelMapper;
     BookService bookService;

     OrderItemServiceImpl orderItemService;

     @BeforeEach
     void initTest(){
         orderItemsRepository = mock(OrderItemsRepository.class);
         modelMapper = mock(ModelMapper.class);
         bookService = mock(BookService.class);
         orderItemService = new OrderItemServiceImpl(orderItemsRepository, modelMapper, bookService);
     }


     @Test
     void whenGetById_thenThrowResourceNotFoundException_ifIdNotExist(){
         int orderItemId = 1;
         when(orderItemsRepository.findById(orderItemId)).thenReturn(Optional.empty());

         ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                 () -> orderItemService.getById(orderItemId));
         assertThat(exception.getMessage(), is("Not Found Order Item With ID: " + orderItemId));
     }

     @Test
     void whenGetById_shouldReturnOrderItem_ifIdExisted(){
         OrderItems orderItems = mock(OrderItems.class);
         int orderItemId = 1;
         when(orderItemsRepository.findById(orderItemId)).thenReturn(Optional.of(orderItems));
         assertThat(orderItemService.getById(orderItemId), is(orderItems));
     }

     @Test
     void whenAdd_shouldReturnNewOrderItems(){
         CartItem cartItem = mock(CartItem.class);
         OrderItems orderItems = mock(OrderItems.class);
         OrderItems savedOrderItem = mock(OrderItems.class);
         int quantity = 3;
         float price = 3.3f;
         Books books = mock(Books.class);
         when(cartItem.getBooks()).thenReturn(books);
         when(books.getQuantity()).thenReturn(quantity);
         when(books.getBookPrice()).thenReturn(price);


         when(modelMapper.map(cartItem, OrderItems.class)).thenReturn(orderItems);
         when(orderItemsRepository.save(orderItems)).thenReturn(savedOrderItem);

         assertThat(orderItemService.add(cartItem), is(savedOrderItem));

     }


//     @Test
//     void whenSaveOrderItem_shouldUpdateOrderItem(){
//         Books books = mock(Books.class);
//         Orders orders = mock(Orders.class);
//         List<OrderItems> orderItemsList = List.of(
//                 OrderItems.builder()
//                           .orderItemsID(1)
//                           .price(3.3f)
//                           .quantity(3)
//                           .books(books)
//                           .orders(orders)
//                           .build(),
//                 OrderItems.builder()
//                         .orderItemsID(2)
//                         .price(3.3f)
//                         .quantity(3)
//                         .books(books)
//                         .orders(orders)
//                         .build()
//         );
//
//         verify(orderItemsList).forEach(
//                 orderItems -> {
//                     orderItems.setOrders(orders);
//                     verify(orderItemsRepository, times(orderItemsList.size())).save(orderItems);
//                 }
//         );
//
//     }
}
