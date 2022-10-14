package com.example.ecommerce_web.service;

import com.example.ecommerce_web.constant.Role;
import com.example.ecommerce_web.constant.UserState;
import com.example.ecommerce_web.exceptions.ConstraintViolateException;
import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.dto.request.ChangePasswordRequestDTO;
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
    UserService userServiceMock;

    @BeforeEach
    void initTest(){
        userRepository = mock(UserRepository.class);
        myDateUtil = mock(MyDateUtil.class);
        encoder = mock(PasswordEncoder.class);
        userLocal = mock(UserLocal.class);
        userServiceMock = mock(UserService.class);
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
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(users));
        assertThat(userService.findLocalUser(), is(users));
    }

    @Test
    void testDeleteAll(){
        userRepository.deleteAll();
        verify(userRepository, times(1)).deleteAll();
    }

    @Test
    void whenAdd_shouldThrowConstrainViolateException_ifUserNameAlreadyExist(){
        UserRequestDTO userRequestDTO = mock(UserRequestDTO.class);
        Date dateOfBirth = new Date();
        Users users = mock(Users.class);
        String roleName = "ADMIN";
        Role role = Role.ADMIN;
        String userName = "khoiproviphehe";

        when(userRequestDTO.getDateOfBirth()).thenReturn(dateOfBirth);
        when(userRequestDTO.getRole()).thenReturn(roleName);
        when(Role.getRole(roleName)).thenReturn(role);
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
        long THREE_MINUTES = 180_000;
        when(userRepository.findById(1)).thenReturn(Optional.of(users));
        Date now = new Date();
        long lockUntil = now.getTime() + THREE_MINUTES;

        users.setUserState(UserState.BLOCK);
        users.setLockTime(new Date(lockUntil));

        verify(users).setUserState(UserState.BLOCK);
        verify(users).setLockTime(new Date(lockUntil));

        when(userRepository.save(users)).thenReturn(savedUser);
        assertThat(userService.block(1).getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void whenLockUser_thenThrowResourceNotFoundException_ifIdNotExiste(){
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> userService.block(1));

        assertThat(exception.getMessage(), is("Not Found User With ID: 1"));
    }


    @Test
    void whenChangePassword_thenThrowResourceNotFoundException_ifPasswordWrong(){
        ChangePasswordRequestDTO changePasswordRequestDTO = mock(ChangePasswordRequestDTO.class);
        String newPassword = "Khoi123";
        String password = "khoipro@140920222";
        String validPassword = "$2a$10$LRjwMTpqCxeEBiU5ztbMseDStATT9Ce7bC381li9Rd46/BUKst0Wm";
        String userName = "khoipro";
        Users users = mock(Users.class);

        when(changePasswordRequestDTO.getNewPassword()).thenReturn(newPassword);
        when(changePasswordRequestDTO.getOldPassword()).thenReturn(password);
        when(userLocal.getLocalUserName()).thenReturn(userName);
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(users));
        when(users.getPassword()).thenReturn(validPassword);

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> userService.changePassword(changePasswordRequestDTO));

        assertThat(exception.getMessage(), is("Password is wrong !!!!"));
    }


    @Test
    void whenChangePassword_thenReturnStatusOk_ifPasswordCorrect(){
        ChangePasswordRequestDTO changePasswordRequestDTO = mock(ChangePasswordRequestDTO.class);
        String newPassword = "Khoi123";
        String password = "khoipro@14092022";
        String validPassword = "$2a$10$LRjwMTpqCxeEBiU5ztbMseDStATT9Ce7bC381li9Rd46/BUKst0Wm";
        String userName = "khoipro";
        Users users = mock(Users.class);

        when(changePasswordRequestDTO.getNewPassword()).thenReturn(newPassword);
        when(changePasswordRequestDTO.getOldPassword()).thenReturn(password);
        when(userLocal.getLocalUserName()).thenReturn(userName);
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(users));
        when(users.getPassword()).thenReturn(validPassword);
        when(encoder.matches(password, validPassword)).thenReturn(true);
        when(encoder.encode(newPassword)).thenReturn(newPassword);

        users.setPassword(newPassword);
        userRepository.save(users);

        verify(users).setPassword(newPassword);
        verify(userRepository).save(users);

        assertThat(userService.changePassword(changePasswordRequestDTO).getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void whenUnlockUser_thenReturnStatusOk(){
        Users users = mock(Users.class);
        int userId = 1;

        when(userRepository.findById(userId)).thenReturn(Optional.of(users));

        users.setUserState(UserState.UNBLOCK);
        users.setLockTime(null);
        userRepository.save(users);

        verify(users).setUserState(UserState.UNBLOCK);
        verify(users).setLockTime(null);
        verify(userRepository).save(users);

        assertThat(userService.unblock(userId).getStatusCode(), is(HttpStatus.OK));
    }
}
