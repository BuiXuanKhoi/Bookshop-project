package com.example.ecommerce_web.service;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.dto.request.LoginRequestDTO;
import com.example.ecommerce_web.model.dto.request.UserRequestDTO;
import com.example.ecommerce_web.model.dto.respond.LoginRespondDTO;
import com.example.ecommerce_web.model.entities.Information;
import com.example.ecommerce_web.model.entities.Users;
import com.example.ecommerce_web.repository.InformationRepository;
import com.example.ecommerce_web.repository.UserRepository;
import com.example.ecommerce_web.security.jwt.JwtUtils;
import com.example.ecommerce_web.service.impl.AuthServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Date;
import java.util.Optional;

public class AuthServiceTest {

    UserRepository userRepository;
    InformationRepository informationRepository;
    AuthenticationManager authenticationManager;
    PasswordEncoder encoder;
    JwtUtils jwtUtils;
    UserService userService;
    InformationService informationService;
    RefreshTokenService refreshTokenService;
    int EXPIRATION;
    AuthServiceImpl authService;

    @BeforeEach
    public void initTest(){
        userRepository = mock(UserRepository.class);
        informationRepository = mock(InformationRepository.class);
        authenticationManager = mock(AuthenticationManager.class);
        encoder = mock(PasswordEncoder.class);
        jwtUtils = mock(JwtUtils.class);
        userService = mock(UserService.class);
        informationService = mock(InformationService.class);
        refreshTokenService = mock(RefreshTokenService.class);
        EXPIRATION = 86_400_000;
        authService = new AuthServiceImpl(userRepository, informationRepository, jwtUtils, encoder, authenticationManager,
                userService, informationService, refreshTokenService, EXPIRATION);
    }

    @Test
    void alwayWrongTest(){
        Assert.assertTrue(1 == 10);
    }


    @Test
    void whenLogin_shouldThrowResourceNotFoundException_ifPasswordWrong(){
        Users users = mock(Users.class);
        LoginRequestDTO loginRequestDTO = mock(LoginRequestDTO.class);
        String userName = "Khoi";
        String loginPassword = "khoipro@14092022";
//        String password = "$2a$10$LRjwMTpqCxeEBiU5ztbMseDStATT9Ce7bC381li9Rd46/BUKst0Wm";
        String password = "khoipro";


        when(loginRequestDTO.getUserName()).thenReturn(userName);
        when(userService.findByUserName(userName)).thenReturn(users);
        when(loginRequestDTO.getPassword()).thenReturn(loginPassword);
        when(users.getPassword()).thenReturn(password);
        when(users.getLockTime()).thenReturn(new Date());

        when(encoder.matches(loginPassword, password)).thenReturn(false);

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> authService.login(loginRequestDTO));

        assertThat(exception.getMessage(), is("Wrong password !!!"));
    }


    @Test
    void whenSignUp_shouldReturnNewUser(){
        Users users = mock(Users.class);
        Information information = mock(Information.class);
        UserRequestDTO userRequestDTO = mock(UserRequestDTO.class);
        String password = "khoipro@14092022";
        String encodePassword = "$2a$10$LRjwMTpqCxeEBiU5ztbMseDStATT9Ce7bC381li9Rd46/BUKst0Wm";

        when(userService.add(userRequestDTO)).thenReturn(users);
        when(informationService.createInformationByExistedUser(userRequestDTO, users)).thenReturn(information);
        when(users.getPassword()).thenReturn(password);
        when(encoder.encode(password)).thenReturn(encodePassword);

        users.setPassword(encodePassword);
        userRepository.save(users);
        informationRepository.save(information);

        verify(users).setPassword(encodePassword);
        verify(userRepository).save(users);
        verify(informationRepository).save(information);

        assertThat(authService.signup(userRequestDTO).getStatusCode(), is(HttpStatus.OK));
    }


    @Test
    void whenLogout_thenDeleteRefreshToken(){
        this.refreshTokenService.deleteByLocalUsers();
        verify(refreshTokenService, times(1)).deleteByLocalUsers();
        assertThat(authService.logout().getStatusCode(), is(HttpStatus.OK));
    }
}
