package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.dto.request.RefreshTokenRequest;
import com.example.ecommerce_web.model.dto.respond.RefreshTokenResponse;
import com.example.ecommerce_web.model.entities.RefreshToken;

import java.sql.Ref;
import java.util.Optional;

public interface RefreshTokenService {


    RefreshToken getByToken(String token);

    RefreshToken createByUserName(String userName);

    RefreshTokenResponse createToken( RefreshTokenRequest request);

    boolean verify(RefreshToken refreshToken);

    void deleteByLocalUsers();

    void deleteRefreshToken(String refreshToken);

}
