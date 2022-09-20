package com.example.ecommerce_web.model.dto.respond;

import com.example.ecommerce_web.constant.OrderState;
import com.example.ecommerce_web.model.entities.OrderItems;
import com.example.ecommerce_web.model.entities.Orders;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRespondDTO {

    private int quantity;

    private int orderId;

    @JsonProperty("state")
    private OrderState orderState;

    @JsonProperty("orderItems")
    List<OrderItemRespondDTO> orderItemRespondDTOS;
}
