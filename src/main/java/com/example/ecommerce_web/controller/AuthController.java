package com.example.ecommerce_web.controller;

import com.example.ecommerce_web.model.dto.request.UserRequestDTO;
import com.example.ecommerce_web.model.dto.request.LoginRequestDTO;
import com.example.ecommerce_web.model.dto.respond.LoginRespondDTO;
import com.example.ecommerce_web.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid UserRequestDTO userRequestDTO){
        return this.authService.signup(userRequestDTO);
    }

    @PostMapping("/login")
    public LoginRespondDTO login(@RequestBody LoginRequestDTO loginRequestDTO){
        return this.authService.login(loginRequestDTO);
    }
}
