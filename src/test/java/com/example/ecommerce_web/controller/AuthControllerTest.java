package com.example.ecommerce_web.controller;


import com.example.ecommerce_web.model.dto.request.UserRequestDTO;
import com.example.ecommerce_web.service.AuthService;
import com.example.ecommerce_web.service.RefreshTokenService;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import java.util.Date;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
public class AuthControllerTest {

    AuthService authService;
    RefreshTokenService refreshTokenService;
    SimpleGrantedAuthority admin;
    ObjectMapper objectMapper;


    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void initTest(){
        authService = mock(AuthService.class);
        refreshTokenService = mock(RefreshTokenService.class);
        admin = new SimpleGrantedAuthority("ADMIN");
        objectMapper = new ObjectMapper();
    }


    @Test
    void whenSignUp_thenReturnStatusBadRequest_ifRequestNotValid() throws Exception {
        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                                                      .userName(null)
                                                      .address("HN")
                                                      .dateOfBirth(new Date())
                                                      .email("khoibuiqn1011@gmail.com")
                                                      .firstName("Xuan Khoi")
                                                      .lastName("Bui")
                                                      .phoneNumber("0988349401")
                                                      .role("ADMIN").build();
        MvcResult result = mockMvc.perform(post("/api/auth/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userRequestDTO))
                        )
                                .andExpect(status().isBadRequest())
                                .andReturn();

        String expected = "{\"message\" : \"username cannot be null\"}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }


    @Test
    void whenSignUp_thenReturnStatusNotFound_ifAddressNotFound() throws Exception{
        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .userName("khoisupperprovip")
                .address("TG")
                .dateOfBirth(new Date())
                .email("khoibuiqn1011@gmail.com")
                .firstName("Xuan Khoi")
                .lastName("Bui")
                .phoneNumber("0988349401")
                .role("ADMIN").build();

        MvcResult result = mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDTO))
        )
                .andExpect(status().isNotFound())
                .andReturn();

        String expected = "{\"message\" : \"Location Not Available !!!\"}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    void whenSignUp_thenReturnStatusBadRequestAndMessage_ifUserNameAlreadyExist() throws Exception{
        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .userName("tuan7")
                .address("HN")
                .dateOfBirth(new Date())
                .email("khoibuiqn1011@gmail.com")
                .firstName("Xuan Khoi")
                .lastName("Bui")
                .phoneNumber("0988349401")
                .role("ADMIN").build();

        MvcResult result = mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDTO))
        )
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

        String expected = "{\"message\" : \"User name already exist !!!!\"}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

    }
}
