package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.entities.RefreshToken;
import com.example.ecommerce_web.model.entities.Users;
import com.example.ecommerce_web.repository.RefreshTokenRepository;
import com.example.ecommerce_web.security.service.UserLocal;
import com.example.ecommerce_web.service.RefreshTokenService;
import com.example.ecommerce_web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;

public class RefreshTokenServiceImpl implements RefreshTokenService {


    RefreshTokenRepository refreshTokenRepository;
    UserService userService;
    UserLocal userLocal;
    long refreshExpiration;

    @Autowired
    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, UserService userService,
                                   UserLocal userLocal, @Value("${ecommerce.app.jwtRefreshExpirationMs}") long refreshExpiration) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userService = userService;
        this.userLocal = userLocal;
        this.refreshExpiration = refreshExpiration;
    }

    @Override
    public RefreshToken getByToken(String token) {
        return this.refreshTokenRepository.findByToken(token)
                                          .orElseThrow(
                                             () ->  new ResourceNotFoundException("Not Found token !!!"));
    }

    @Override
    public String createToken() {
        Users users = userService.findByUserName(userLocal.getLocalUserName());
        return null;
    }

    @Override
    public RefreshToken createRefreshToken() {
        return null;
    }

    @Override
    public void deleteToken(String token) {

    }
}
