package com.example.ecommerce_web.model.dto.respond;

import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.model.entities.OrderItems;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRespondDTO {

    private int orderItemId;
    private float price;
    private int quantity;
    private String bookName;
    private String imageLink;
}
