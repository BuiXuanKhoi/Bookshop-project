package com.example.ecommerce_web.model.alter;


import com.example.ecommerce_web.constant.OrderState;
import com.example.ecommerce_web.model.dto.respond.OrderItemRespondDTO;
import lombok.*;
import org.hibernate.criterion.Order;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ManageOrderRespondDTO {

    private int orderId;
    private String userName;
    private float totalPrice;
    private List<OrderItemRespondDTO> orderItems;
    private Date createDate;
    private Date updateDate;
    private String updateBy;
    private OrderState orderState;

    public ManageOrderRespondDTO(int orderId,  String userName, float totalPrice, Date createDate, Date updateDate, String updateBy, OrderState orderState) {
        this.orderId = orderId;
        this.userName = userName;
        this.totalPrice = totalPrice;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.updateBy = updateBy;
        this.orderState = orderState;
    }
}
