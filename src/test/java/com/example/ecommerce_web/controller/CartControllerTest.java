package com.example.ecommerce_web.controller;

import com.example.ecommerce_web.mapper.CartItemMapper;
import com.example.ecommerce_web.model.dto.request.CartItemRequestDTO;
import com.example.ecommerce_web.model.dto.respond.CartItemRespondDTO;
import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.model.entities.CartItem;
import com.example.ecommerce_web.service.BookService;
import com.example.ecommerce_web.service.CartItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
public class CartControllerTest {
    CartItemService cartItemService;
    CartItemMapper cartItemMapper;
    SimpleGrantedAuthority admin;
    SimpleGrantedAuthority customer;
    BookService bookService;
    @Autowired
    private MockMvc mvc;

    @BeforeEach
    private void init(){
        cartItemService = mock(CartItemService.class);
        admin = new SimpleGrantedAuthority("ADMIN");
        customer = new SimpleGrantedAuthority("CUSTOMER");
        cartItemMapper = mock(CartItemMapper.class);
        bookService = mock(BookService.class);
    }

    @Test
    public void whenAddBookItemToCart_thenReturnStatementThatAddActionIsSuccess () throws Exception {
        CartItem cartItem = mock(CartItem.class);
        CartItemRespondDTO cartItemRespondDTO = mock(CartItemRespondDTO.class);
        CartItemRequestDTO cartItemRequestDTO = CartItemRequestDTO.builder()
                                                                    .quantity(2)
                                                                    .bookId(20).build();
        ObjectMapper objectMapper = new ObjectMapper();

        when(cartItemService.add(cartItemRequestDTO)).thenReturn(cartItem);
        when(cartItemMapper.toDTO(cartItem)).thenReturn(cartItemRespondDTO);

        mvc.perform(MockMvcRequestBuilders.post("/api/carts")
                                            .with(user("tuan2").authorities(customer))
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .content(objectMapper.writeValueAsString(cartItemRequestDTO))
        )
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    public void whenAddBookItemToCart_throwsConstraintViolateException_IfCartItemAlreadyExisted () throws Exception {
        CartItem cartItem = mock(CartItem.class);
        CartItemRespondDTO cartItemRespondDTO = mock(CartItemRespondDTO.class);
        CartItemRequestDTO cartItemRequestDTO = CartItemRequestDTO.builder()
                .quantity(2)
                .bookId(20).build();
        ObjectMapper objectMapper = new ObjectMapper();

        when(cartItemService.add(cartItemRequestDTO)).thenReturn(cartItem);
        when(cartItemMapper.toDTO(cartItem)).thenReturn(cartItemRespondDTO);

        mvc.perform(MockMvcRequestBuilders.post("/api/carts")
                .with(user("tuan2").authorities(customer))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartItemRequestDTO))
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{'message':'Cannot Add To Cart Due To Already Existed Cart Item !!!'}"))
                .andDo(print());
    }

//    @Test
////    public void whenAddBookItemToCart_throwsConstraintViolateException_IfBookIsOutOfStock () throws Exception {
////        CartItem cartItem = mock(CartItem.class);
////        int bookId = 3;
////        CartItemRespondDTO cartItemRespondDTO = mock(CartItemRespondDTO.class);
////
////        Books books = bookService.getById(bookId);
////        books.setQuantity(8);
////
////        CartItemRequestDTO cartItemRequestDTO = CartItemRequestDTO.builder()
////                .quantity(10)
////                .bookId(bookId).build();
////        ObjectMapper objectMapper = new ObjectMapper();
////
////        when(cartItemService.add(cartItemRequestDTO)).thenReturn(cartItem);
////        when(cartItemMapper.toDTO(cartItem)).thenReturn(cartItemRespondDTO);
////
////        mvc.perform(MockMvcRequestBuilders.post("/api/carts")
////                .with(user("tuan2").authorities(customer))
////                .contentType(MediaType.APPLICATION_JSON)
////                .content(objectMapper.writeValueAsString(cartItemRequestDTO))
////        )
////                .andExpect(status().isBadRequest())
////                //.andExpect(content().json("{'message':'Cannot add to cart due to out of stock !!!'}"))
////                .andDo(print());
////    }

    @Test
    public void whenSendRequestToGetListCart_ThenReturnListCartItem () throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/carts")
                                .with(user("tuan2").authorities(customer))
        )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void whenSendRequestToUpdateCartItem_thenReturnCartItemHasBeenUpdated () throws Exception {
        int cartItemId = 63;
        String addQuantity = "1";
        CartItem cartItem = mock(CartItem.class);
        int quantityConverted = Integer.parseInt(addQuantity);
        CartItemRespondDTO cartItemRespondDTO = mock(CartItemRespondDTO.class);

        when(cartItemService.update(cartItemId,quantityConverted)).thenReturn(cartItem);
        when(cartItemMapper.toDTO(cartItem)).thenReturn(cartItemRespondDTO);

        mvc.perform(MockMvcRequestBuilders.put("/api/carts/"+cartItemId+"/increase")
                                            .param("quantity",addQuantity)
                                            .with(user("tuan2").authorities(customer))
        )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void whenSendRequestToUpdateCartItem_throwsNotFoundError_IfCartItemIdIsInvalid() throws Exception {
        int cartItemId = 100;
        String addQuantity = "1";
        CartItem cartItem = mock(CartItem.class);
        int quantityConverted = Integer.parseInt(addQuantity);
        CartItemRespondDTO cartItemRespondDTO = mock(CartItemRespondDTO.class);

        when(cartItemService.update(cartItemId,quantityConverted)).thenReturn(cartItem);
        when(cartItemMapper.toDTO(cartItem)).thenReturn(cartItemRespondDTO);

        mvc.perform(MockMvcRequestBuilders.put("/api/carts/"+cartItemId+"/increase")
                .param("quantity",addQuantity)
                .with(user("tuan2").authorities(customer))
        )
                .andExpect(status().isNotFound())
                .andExpect(content().json("{'message':'Not Found Cart Item With ID: 61'}"))
                .andDo(print());
    }

//    @Test
//    public void whenSendRequestToUpdateCartItem_throwsConstraintViolateException_IfCartItemQuantityIsLargerThanBookQuantity() throws Exception {
//        int cartItemId = 61;
//        String addQuantity = "1";
//        CartItem cartItem = mock(CartItem.class);
//        int quantityConverted = Integer.parseInt(addQuantity);
//        CartItemRespondDTO cartItemRespondDTO = mock(CartItemRespondDTO.class);
//
//        when(cartItemService.update(cartItemId,quantityConverted)).thenReturn(cartItem);
//        when(cartItemMapper.toDTO(cartItem)).thenReturn(cartItemRespondDTO);
//
//        mvc.perform(MockMvcRequestBuilders.put("/api/carts/"+cartItemId+"/increase")
//                .param("quantity",addQuantity)
//                .with(user("tuan2").authorities(customer))
//        )
//                .andExpect(status().isNotFound())
//                .andExpect(content().json("{'message':'Not Found Cart Item With ID: 61'}"))
//                .andDo(print());
//    }
@Test
public void whenSendRequestToChangeCartItem_thenReturnCartItemHasBeenChanged () throws Exception {
    int cartItemId = 63;
    String changeQuantity = "3";
    CartItem cartItem = mock(CartItem.class);
    int quantityConverted = Integer.parseInt(changeQuantity);
    CartItemRespondDTO cartItemRespondDTO = mock(CartItemRespondDTO.class);

    when(cartItemService.update(cartItemId,quantityConverted)).thenReturn(cartItem);
    when(cartItemMapper.toDTO(cartItem)).thenReturn(cartItemRespondDTO);

    mvc.perform(MockMvcRequestBuilders.put("/api/carts/"+cartItemId+"/change")
            .param("quantity",changeQuantity)
            .with(user("tuan2").authorities(customer))
    )
            .andExpect(status().isOk())
            .andDo(print());
}

    @Test
    public void whenSendRequestToChangeCartItem_throwsNotFoundError_IfCartItemIdIsInvalid() throws Exception {
        int cartItemId = 100;
        String changeQuantity = "1";
        CartItem cartItem = mock(CartItem.class);
        int quantityConverted = Integer.parseInt(changeQuantity);
        CartItemRespondDTO cartItemRespondDTO = mock(CartItemRespondDTO.class);

        when(cartItemService.update(cartItemId,quantityConverted)).thenReturn(cartItem);
        when(cartItemMapper.toDTO(cartItem)).thenReturn(cartItemRespondDTO);

        mvc.perform(MockMvcRequestBuilders.put("/api/carts/"+cartItemId+"/increase")
                .param("quantity",changeQuantity)
                .with(user("tuan2").authorities(customer))
        )
                .andExpect(status().isNotFound())
                .andExpect(content().json("{'message':'Not Found Cart Item With ID: 100'}"))
                .andDo(print());
    }


    @Test
    public void whenSendRequestToDeleteCartItem_thenReturnCartItemHasBeenDeleted () throws Exception {
        int cartItemId = 61;

        mvc.perform(MockMvcRequestBuilders.delete("/api/carts/"+cartItemId)
                .with(user("tuan2").authorities(customer))
        )
                .andExpect(status().isOk())
                .andExpect(content().json("{'message':'Delete Cart Item Successfully !!!'}"))
                .andDo(print());
    }

    @Test
    public void whenSendRequestToDeleteCartItem_throwsNotFoundError_ifCartItemIdIsInvalid () throws Exception {
        int cartItemId = 61;

        mvc.perform(MockMvcRequestBuilders.delete("/api/carts/"+cartItemId)
                .with(user("tuan2").authorities(customer))
        )
                .andExpect(status().isNotFound())
                .andExpect(content().json("{'message':'Not Found Cart Item With ID: 61'}"))
                .andDo(print());
    }
}
