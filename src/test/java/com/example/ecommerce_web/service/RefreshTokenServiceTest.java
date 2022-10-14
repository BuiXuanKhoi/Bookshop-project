package com.example.ecommerce_web.service;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.entities.RefreshToken;
import com.example.ecommerce_web.model.entities.Users;
import com.example.ecommerce_web.repository.RefreshTokenRepository;
import com.example.ecommerce_web.security.jwt.JwtUtils;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;

import com.example.ecommerce_web.service.impl.RefreshTokenServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class RefreshTokenServiceTest {

    RefreshTokenRepository refreshTokenRepository;
    UserService userService;
    JwtUtils jwtUtils;
    long refreshExpiration;
    RefreshTokenServiceImpl refreshTokenService;

    @BeforeEach
    void initTest(){
        refreshTokenRepository = mock(RefreshTokenRepository.class);
        userService = mock(UserService.class);
        jwtUtils = mock(JwtUtils.class);
        refreshExpiration = 172_800_000;
        refreshTokenService = new RefreshTokenServiceImpl(refreshTokenRepository, userService, refreshExpiration, jwtUtils);
    }


    @Test
    void whenGetByToken_thenThrowResourceNotFoundException_ifTokenNotExisted(){
        String token = "123";
        when(refreshTokenRepository.findByToken(token)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> refreshTokenService.getByToken(token));

        assertThat(exception.getMessage(), is("Not Found token !!!"));
    }


    @Test
    void whenGetByToken_thenReturnRefreshToken_ifTokenExisted(){
        String token = "123";
        RefreshToken refreshToken = mock(RefreshToken.class);
        when(refreshTokenRepository.findByToken(token)).thenReturn(Optional.of(refreshToken));

        assertThat(refreshTokenService.getByToken(token), is(refreshToken));

    }


    @Test
    void testDeleteRefreshToken(){
        String refreshToken = "123";

        refreshTokenRepository.deleteByToken(refreshToken);
        verify(refreshTokenRepository).deleteByToken(refreshToken);
    }

    @Test
    void testDeleteByLocalUser(){
        Users users = mock(Users.class);
        when(userService.findLocalUser()).thenReturn(users);

        refreshTokenRepository.deleteByUsers(users.getUserId());
        verify(refreshTokenRepository).deleteByUsers(users.getUserId());
    }


    @Test
    void whenCreateByUserName_thenReturnNewFreshToken(){
        Date now = new Date();
        Date expiresDuration = new Date(now.getTime() + refreshExpiration);
        Users users = mock(Users.class);
        String userName = "khoi";
        String token = "123";
        RefreshToken refreshToken = mock(RefreshToken.class);
        RefreshToken savedRefreshToken = mock(RefreshToken.class);

        when(userService.findByUserName(userName)).thenReturn(users);
        when(refreshTokenRepository.save(refreshToken)).thenReturn(savedRefreshToken);

        assertThat(refreshTokenService.createByUserName(userName).getId(), is(savedRefreshToken.getId()));
    }
}
