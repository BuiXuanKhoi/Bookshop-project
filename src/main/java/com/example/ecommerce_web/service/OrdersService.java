package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.alter.ManageOrderRespondDTO;
import com.example.ecommerce_web.model.dto.respond.PageManageOrder;
import com.example.ecommerce_web.model.entities.Orders;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrdersService {
    Orders add();
    List<Orders> findAllByLocalUser();
    PageManageOrder getPageForManage(int page, String search);
    Orders updateState(int orderId);
    public Orders findById(int id);
}
