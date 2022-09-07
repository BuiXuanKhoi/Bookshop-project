package com.example.ecommerce_web.model.dto.respond;

import com.example.ecommerce_web.model.OrderState;
import com.example.ecommerce_web.model.entities.OrderItems;
import com.example.ecommerce_web.model.entities.Orders;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRespondDTO {

    private int quantity;

    private int orderId;

    @JsonProperty("state")
    private OrderState orderState;

    @JsonProperty("orderItems")
    List<OrderItemRespondDTO> orderItemRespondDTOS;



    public OrderRespondDTO(Orders orders){
        List<OrderItems> orderItemsList = orders.getOrderItems();

        List<OrderItemRespondDTO> respondDTOList = orderItemsList.stream()
                .map(OrderItemRespondDTO::new)
                .collect(Collectors.toList());

        int orderId = orders.getOrderId();
        int orderQuantity = respondDTOList.size();

        setQuantity(orderQuantity);
        setOrderId(orderId);
        setOrderItemRespondDTOS(respondDTOList);
        setOrderState(orders.getOrderState());
    }

}
