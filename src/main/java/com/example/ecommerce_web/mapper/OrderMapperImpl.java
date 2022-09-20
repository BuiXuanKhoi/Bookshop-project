package com.example.ecommerce_web.mapper;

import com.example.ecommerce_web.model.dto.respond.OrderItemRespondDTO;
import com.example.ecommerce_web.model.dto.respond.OrderRespondDTO;
import com.example.ecommerce_web.model.entities.OrderItems;
import com.example.ecommerce_web.model.entities.Orders;
import com.example.ecommerce_web.validator.ListValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Qualifier("order")
@Service
public class OrderMapperImpl implements OrderMapper {

    OrderItemMapper orderItemMapper;

    @Autowired
    public OrderMapperImpl(OrderItemMapper orderItemMapper) {
        this.orderItemMapper = orderItemMapper;
    }

    @Override
    public OrderRespondDTO toDTO(Orders entity) {
        List<OrderItems> orderItemsList = entity.getOrderItems();
        ListValidator<OrderItems> listItemValid = ListValidator.ofList(orderItemsList);

        List<OrderItemRespondDTO> respondDTOList = listItemValid.ifNotEmpty()
                                                                .stream()
                                                                .map(orderItemMapper::toDTO)
                                                                .collect(Collectors.toList());
        int orderQuantity = respondDTOList.size();

        return OrderRespondDTO.builder()
                              .orderId(entity.getOrderId())
                              .orderState(entity.getOrderState())
                              .orderItemRespondDTOS(respondDTOList)
                              .quantity(orderQuantity)
                              .build();
    }
}
