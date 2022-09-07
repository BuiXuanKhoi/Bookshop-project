package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.dto.request.UserRequestDTO;
import com.example.ecommerce_web.model.dto.request.LoginRequestDTO;
import com.example.ecommerce_web.model.dto.respond.LoginRespondDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    LoginRespondDTO login(LoginRequestDTO loginRequestDTO);

    ResponseEntity<?> signup(UserRequestDTO userRequestDTO);


}
