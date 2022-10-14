package com.example.ecommerce_web.repository;

import com.example.ecommerce_web.model.alter.ManageOrderRespondDTO;
import com.example.ecommerce_web.model.entities.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Integer>{

    @Query(value = "" +
            "select new com.example.ecommerce_web.model.alter.ManageOrderRespondDTO(" +
            "ord.orderId, usr.userName, 0.0f, ord.createDate, ord.updateDate, usr.userName, ord.orderState) " +
            "from Orders ord " +
            "join ord.users usr " +
            "where lower(usr.userName) like %:search% " +
            "order by ord.orderId asc")
    Page<ManageOrderRespondDTO> getPageManageOrders(Pageable pageable, String search);
}
