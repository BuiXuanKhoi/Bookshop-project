package com.example.ecommerce_web.service;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.mapper.InformationMapper;
import com.example.ecommerce_web.model.dto.request.ModifyUserRequestDTO;
import com.example.ecommerce_web.model.dto.request.UserRequestDTO;
import com.example.ecommerce_web.model.entities.Users;
import com.example.ecommerce_web.model.entities.Information;
import com.example.ecommerce_web.repository.InformationRepository;
import com.example.ecommerce_web.security.service.UserLocal;
import com.example.ecommerce_web.service.impl.InformationServiceImpl;
import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Assertions;

import java.util.Optional;

public class InformationServiceTest {
    InformationRepository informationRepository;
    UserLocal userLocal;
    InformationMapper informationMapper;
    UserService userService;
    InformationServiceImpl informationService;

    @BeforeEach
    void init(){
        informationMapper = mock(InformationMapper.class);
        informationRepository = mock(InformationRepository.class);
        userLocal = mock(UserLocal.class);
        userService = mock(UserService.class);
        informationService = new InformationServiceImpl(informationRepository,userLocal,informationMapper,userService);
    }

    @Test
    void whenUpdateInformation_thenReturnNewInformation () {
        String userName = "Tuan";
        Users users = mock(Users.class);
        ModifyUserRequestDTO modifyUserRequestDTO = mock(ModifyUserRequestDTO.class);
        Information information = mock(Information.class);
        Information updatedInformation = mock(Information.class);

        when(userLocal.getLocalUserName()).thenReturn(userName);
        when(userService.findByUserName(userName)).thenReturn(users);
        when(informationMapper.toExistedInformation(modifyUserRequestDTO, users.getInformation())).thenReturn(information);
        when(informationRepository.save(information)).thenReturn(updatedInformation);
        assertThat(informationService.update(modifyUserRequestDTO),is(updatedInformation));
    }

    @Test
    void whenCreatedInformationByExistedUser_thenReturnNewUsersWithAdminRoles () {
        Information information =  mock(Information.class);
        UserRequestDTO userRequestDTO = mock(UserRequestDTO.class);
        Users users = mock(Users.class);

        when(informationMapper.fromUserRequest(userRequestDTO)).thenReturn(information);
        information.setUsers(users);
        verify(information).setUsers(users);
        assertThat(informationService.createInformationByExistedUser(userRequestDTO,users),is(information));
    }

    @Test
    void whenGetInformationByID_thenThrowResourceNotFoundException_ifIdNotExists () {
        int id = 3;
        when(informationRepository.findById(id)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                ()-> informationService.getById(id));
        assertThat(exception.getMessage(),is("Information not exists with ID: " + id));
    }

    @Test
    void whenGetInformationByID_thenReturnInformation_ifIdExists () {
        int id = 3;
        Information information = mock(Information.class) ;
        when(informationRepository.findById(id)).thenReturn(Optional.of(information));
        assertThat(informationService.getById(id),is(information));
    }

    @Test
    void whenGetInformationByLocalUser_thenReturnUserInformation () {
        String userName ="tuan";
        Users users = mock(Users.class);
        Information information = mock(Information.class);

        when(userLocal.getLocalUserName()).thenReturn(userName);
        when(userService.findByUserName(userName)).thenReturn(users);
        when(users.getInformation()).thenReturn(information);
        assertThat(informationService.getByLocalUser(),is(information));
    }
}
