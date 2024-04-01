package com.example.ecommerce_web.controller;


import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.mapper.OrderMapper;
import com.example.ecommerce_web.model.dto.respond.OrderRespondDTO;
import com.example.ecommerce_web.model.entities.Orders;
import com.example.ecommerce_web.service.OrdersService;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
public class OrderControllerTest {
    OrderMapper orderMapper;
    OrdersService ordersService;
    SimpleGrantedAuthority admin;
    SimpleGrantedAuthority customer;

    @BeforeEach
    private void init() {
        orderMapper = mock(OrderMapper.class);
        ordersService = mock(OrdersService.class);
        admin = new SimpleGrantedAuthority("ADMIN");
        customer = new SimpleGrantedAuthority("CUSTOMER");
    }

    @Autowired
    private MockMvc mvc;

//    @Test
//    public void whenSendRequestToAddedOrder_thenReturnCustomerOrderList () throws Exception {
//        OrderRespondDTO orderRespondDTO = mock(OrderRespondDTO.class);
//        Orders orders = mock(Orders.class);
//
//        when(ordersService.add()).thenReturn(orders);
//        when(orderMapper.toDTO(orders)).thenReturn(orderRespondDTO);
//
//        mvc.perform(MockMvcRequestBuilders.post("/api/orders")
//                                            .with(user("tuan2").authorities(customer))
//        )
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
//
//    @Test
//    public void whenSendRequestToAddedOrder_throwsResourceNotFoundException_IfCustomerDoesNotBuyAnyBookBefore () throws Exception {
//        OrderRespondDTO orderRespondDTO = mock(OrderRespondDTO.class);
//        Orders orders = mock(Orders.class);
//
//        when(ordersService.add()).thenReturn(orders);
//        when(orderMapper.toDTO(orders)).thenReturn(orderRespondDTO);
//
//        mvc.perform(MockMvcRequestBuilders.post("/api/orders")
//                .with(user("khoiprovipsupper").authorities(customer))
//        )
//                .andExpect(status().isNotFound())
//                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
//                .andDo(print());
//    }
//
//    @Test
//    public void whenSendRequestToGetOrderList_returnOrderList () throws Exception {
//        mvc.perform(MockMvcRequestBuilders.get("/api/orders")
//                .with(user("tuan2").authorities(customer))
//        )
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
//
//    @Test
//    public void whenSendRequestToGetOrderList_throwsResourceNotFoundException_IfUserDoesNotHavePurchaseHistory () throws Exception {
//        mvc.perform(MockMvcRequestBuilders.get("/api/orders")
//                .with(user("lfsdfdlfsd").authorities(admin))
//        )
//                .andExpect(status().isNotFound())
//                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
//                .andExpect(content().json("{'message':'List is Empty !!!'}"))
//                .andDo(print());
//    }
//
//    @Test
//    public void whenSendRequestToUpdateOrderState_throwsFobiddenError_IfUserIsNotAdmin ()  throws Exception {
//        Orders orders = mock(Orders.class);
//        int orderId = 38 ;
//        OrderRespondDTO orderRespondDTO = mock(OrderRespondDTO.class);
//
//        when(ordersService.updateState(orderId)).thenReturn(orders);
//        when(orderMapper.toDTO(orders)).thenReturn(orderRespondDTO);
//
//        mvc.perform(MockMvcRequestBuilders.put("/api/orders/"+orderId)
//                .with(user("tuan2").authorities(customer))
//        )
//                .andExpect(status().isForbidden())
//                .andDo(print());
//    }
//
//    @Test
//    public void whenSendRequestToUpdateOrderState_returnOrderStateHasBeenUpdated ()  throws Exception {
//        Orders orders = mock(Orders.class);
//        int orderId = 38 ;
//        OrderRespondDTO orderRespondDTO = mock(OrderRespondDTO.class);
//
//        when(ordersService.updateState(orderId)).thenReturn(orders);
//        when(orderMapper.toDTO(orders)).thenReturn(orderRespondDTO);
//
//        mvc.perform(MockMvcRequestBuilders.put("/api/orders/"+orderId)
//                .with(user("lfsdfdlfsd").authorities(admin))
//        )
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
//
//    @Test
//    public void whenSendRequestToUpdateOrderState_throwsResourceNotFoundException_ifOrderIdIsInvalid ()  throws Exception {
//        Orders orders = mock(Orders.class);
//        int orderId = 100 ;
//        OrderRespondDTO orderRespondDTO = mock(OrderRespondDTO.class);
//
//        when(ordersService.updateState(orderId)).thenReturn(orders);
//        when(orderMapper.toDTO(orders)).thenReturn(orderRespondDTO);
//
//        mvc.perform(MockMvcRequestBuilders.put("/api/orders/"+orderId)
//                .with(user("lfsdfdlfsd").authorities(admin))
//        )
//                .andExpect(status().isNotFound())
//                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
//                .andExpect(content().json("{'message':'Order Not Found With ID: 100'}"))
//                .andDo(print());
//    }
//
//    @Test
//    public void whenSendRequestGetOrderPage_returnOrderPageBasedOnPageAndSearch () throws Exception {
//        String pages = "0";
//        String searchName = "%";
//
//        mvc.perform(MockMvcRequestBuilders.get("/api/orders/manage")
//                                            .param("page",pages)
//                                            .param("search",searchName)
//                                            .with(user("lfsdfdlfsd").authorities(admin))
//        )
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
//
//    @Test
//    public void whenSendRequestGetOrderPage_throwsNotFoundError_ifUserDoesNotExist () throws Exception {
//        String pages = "0";
//        String searchName = "12637841683476";
//
//        mvc.perform(MockMvcRequestBuilders.get("/api/orders/manage")
//                .param("page",pages)
//                .param("search",searchName)
//                .with(user("lfsdfdlfsd").authorities(admin))
//        )
//                .andExpect(status().isNotFound())
//                .andDo(print());
//    }
}
