package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.RefreshTokenExpiredException;
import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.dto.request.RefreshTokenRequest;
import com.example.ecommerce_web.model.dto.respond.RefreshTokenResponse;
import com.example.ecommerce_web.model.entities.RefreshToken;
import com.example.ecommerce_web.model.entities.Users;
import com.example.ecommerce_web.repository.RefreshTokenRepository;
import com.example.ecommerce_web.security.jwt.JwtUtils;
import com.example.ecommerce_web.service.RefreshTokenService;
import com.example.ecommerce_web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {


    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final long refreshExpiration;

    @Autowired
    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, UserService userService
                                    , @Value("${ecommerce.app.jwtRefreshExpirationMs}") long refreshExpiration, JwtUtils jwtUtils) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userService = userService;
        this.refreshExpiration = refreshExpiration;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public RefreshToken getByToken(String token) {
        return this.refreshTokenRepository.findByToken(token)
                                          .orElseThrow(
                                             () ->  new ResourceNotFoundException("Not Found token !!!"));
    }



    @Override
    public RefreshToken createByUserName(String userName) {
        Date now = new Date();
        Users users = userService.findByUserName(userName);
        Date expiresDuration = new Date(now.getTime() + refreshExpiration);
        String token = UUID.randomUUID().toString();

        RefreshToken refreshToken = RefreshToken.builder()
                                                .expired(expiresDuration)
                                                .token(token)
                                                .users(users)
                                                .build();
        return this.refreshTokenRepository.save(refreshToken);
    }

    @Override
    @Transactional
    public RefreshTokenResponse createToken(RefreshTokenRequest request) {

        String oldRefreshToken = request.getRefreshToken();
        RefreshToken refreshToken = getByToken(oldRefreshToken);
        String userName = refreshToken.getUsers().getUserName();

        String jwtToken = Optional.of(refreshToken)
                                  .filter(this::isNotExpired)
                                  .map(RefreshToken::getUsers)
                                  .map(Users::getUserName)
                                  .map(jwtUtils::generateTokenByUserName)
                                  .orElseThrow(RefreshTokenExpiredException::new);

        deleteRefreshToken(oldRefreshToken);
        RefreshToken newRefreshToken = createByUserName(userName);

        return RefreshTokenResponse.builder()
                                   .jwtToken(jwtToken)
                                   .refreshToken(newRefreshToken.getToken())
                                   .tokenType("Bearer")
                                   .build();
    }

    @Override
    public boolean isNotExpired(RefreshToken refreshToken) {
        Date now = new Date();
        return refreshToken.getExpired().after(now);
    }


    @Override
    @Transactional
    public void deleteByLocalUsers() {
        Users users = userService.findLocalUser();
        this.refreshTokenRepository.deleteByUsers(users.getUserId());
    }

    @Override
    @Transactional
    public void deleteRefreshToken(String refreshToken) {
        this.refreshTokenRepository.deleteByToken(refreshToken);
    }


}
