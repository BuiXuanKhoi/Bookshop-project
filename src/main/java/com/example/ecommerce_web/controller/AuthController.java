package com.example.ecommerce_web.controller;

import com.example.ecommerce_web.model.dto.request.RefreshTokenRequest;
import com.example.ecommerce_web.model.dto.request.UserRequestDTO;
import com.example.ecommerce_web.model.dto.request.LoginRequestDTO;
import com.example.ecommerce_web.model.dto.respond.LoginRespondDTO;
import com.example.ecommerce_web.model.dto.respond.MessageRespond;
import com.example.ecommerce_web.model.dto.respond.RefreshTokenResponse;
import com.example.ecommerce_web.service.AuthService;
import com.example.ecommerce_web.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(maxAge = 3600, origins = "*")
public class AuthController {

    AuthService authService;
    RefreshTokenService refreshTokenService;

    @Autowired
    public AuthController(AuthService authService, RefreshTokenService refreshTokenService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid UserRequestDTO userRequestDTO){
        return this.authService.signup(userRequestDTO);
    }

    @PostMapping("/login")
    public LoginRespondDTO login(@RequestBody @Valid LoginRequestDTO loginRequestDTO){
        return this.authService.login(loginRequestDTO);
    }

    @PostMapping("/token")
    public RefreshTokenResponse refreshToken(@RequestBody @Valid RefreshTokenRequest request){
        return this.refreshTokenService.createToken(request);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(){
        return this.authService.logout();
    }
}
