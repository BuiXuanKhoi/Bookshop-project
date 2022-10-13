package com.example.ecommerce_web.service;

import com.example.ecommerce_web.constant.Role;
import com.example.ecommerce_web.constant.UserState;
import com.example.ecommerce_web.exceptions.ConstraintViolateException;
import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.dto.request.UserRequestDTO;
import com.example.ecommerce_web.model.entities.Users;
import com.example.ecommerce_web.repository.UserRepository;
import com.example.ecommerce_web.security.service.UserLocal;
import com.example.ecommerce_web.service.impl.UserServiceImpl;
import com.example.ecommerce_web.utils.MyDateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Date;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    UserRepository userRepository;
    MyDateUtil myDateUtil;
    PasswordEncoder encoder;
    UserLocal userLocal;
    UserServiceImpl userService;

    @BeforeEach
    void initTest(){
        userRepository = mock(UserRepository.class);
        myDateUtil = mock(MyDateUtil.class);
        encoder = mock(PasswordEncoder.class);
        userLocal = mock(UserLocal.class);
        userService = new UserServiceImpl(userRepository, myDateUtil, encoder, userLocal);
    }

    @Test
    void whenFindById_shouldThrowResourceNotFoundException_ifIdNotExist(){
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> userService.findById(1));

        assertThat(exception.getMessage(), is("Not Found User With ID: 1"));
    }

    @Test
    void whenFindById_shouldReturnUsers_ifIdExisted(){
        Users users = mock(Users.class);
        when(userRepository.findById(1)).thenReturn(Optional.of(users));
        assertThat(userService.findById(1), is(users));
    }


    @Test
    void whenFindByUserName_shouldThrowResourceNotFoundException_ifUserNameNotExist(){
        when(userRepository.findByUserName("Khoi")).thenReturn(Optional.empty());
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> userService.findByUserName("Khoi"));

        assertThat(exception.getMessage(), is("Not Found User With Name: Khoi"));
    }

    @Test
    void whenFindByUserName_shouldReturnUser_ifUserNameExisted(){
        Users users = mock(Users.class);
        when(userRepository.findByUserName("Khoi")).thenReturn(Optional.of(users));
        assertThat(userService.findByUserName("Khoi"), is(users));
    }

    @Test
    void whenFindByLocal_shouldReturnLocalUser(){
        String userName = "Khoi";
        Users users = mock(Users.class);
        when(userLocal.getLocalUserName()).thenReturn(userName);
        when(userService.findByUserName(userName)).thenReturn(users);
        assertThat(userService.findLocalUser(), is(users));
    }

    @Test
    void testDeleteAll(){
        verify(userRepository).deleteAll();
    }

    @Test
    void whenAdd_shouldThrowConstrainViolateException_ifUserNameAlreadyExist(){
        UserRequestDTO userRequestDTO = mock(UserRequestDTO.class);
        Users users = mock(Users.class);
        String userName = "khoiproviphehe";

        when(userRequestDTO.getUserName()).thenReturn(userName);
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(users));
        ConstraintViolateException exception = Assertions.assertThrows(ConstraintViolateException.class,
                () -> userService.add(userRequestDTO));

        assertThat(exception.getMessage(), is("User name already exist !!!!"));
    }


    @Test
    void whenLockUser_thenReturnStatusOk(){
        Users users = mock(Users.class);
        Users savedUser = mock(Users.class);
        long THREE_MINUTES = 180000;
        when(userService.findById(1)).thenReturn(users);
        Date now = new Date();
        long lockUntil = now.getTime() + THREE_MINUTES;

        verify(users).setUserState(UserState.BLOCK);
        verify(users).setLockTime(new Date(lockUntil));

        when(userRepository.save(users)).thenReturn(savedUser);
        assertThat(userService.block(1).getStatusCode(), is(HttpStatus.OK));
    }
}
