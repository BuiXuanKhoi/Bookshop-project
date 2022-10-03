package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.entities.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {


    RefreshToken getByToken(String token);


    String createToken();

    RefreshToken createRefreshToken();

    void deleteToken(String token);


}
