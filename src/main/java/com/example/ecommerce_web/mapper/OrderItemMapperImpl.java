package com.example.ecommerce_web.mapper;

import com.example.ecommerce_web.model.dto.respond.OrderItemRespondDTO;
import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.model.entities.OrderItems;
import org.springframework.stereotype.Service;

@Service
public class OrderItemMapperImpl implements OrderItemMapper {


    @Override
    public OrderItemRespondDTO toDTO(OrderItems entity) {
        Books books = entity.getBooks();
        int oderItemId = entity.getOrderItemsID();
        float price = entity.getPrice();
        int quanity = entity.getQuantity();
        String bookName = books.getBookName();
        String imageLink = books.getImageLink();

        return OrderItemRespondDTO.builder()
                                  .orderItemId(oderItemId)
                                  .price(price)
                                  .quantity(quanity)
                                  .bookName(bookName)
                                  .imageLink(imageLink)
                                  .build();
    }
}
