package com.example.ecommerce_web.service;

import com.example.ecommerce_web.mapper.InformationMapper;
import com.example.ecommerce_web.model.dto.request.ModifyUserRequestDTO;
import com.example.ecommerce_web.model.entities.Information;
import com.example.ecommerce_web.model.entities.Users;
import com.example.ecommerce_web.repository.InformationRepository;
import com.example.ecommerce_web.security.service.UserLocal;
import com.example.ecommerce_web.service.impl.InformationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
public class InformationServiceTest {

    InformationRepository informationRepository;
    UserLocal userLocal;
    InformationMapper informationMapper;
    UserService userService;
    InformationServiceImpl informationService;


    @BeforeEach
    void initTest(){
        informationRepository = mock(InformationRepository.class);
        userLocal = mock(UserLocal.class);
        informationMapper = mock(InformationMapper.class);
        userService = mock(UserService.class);
        informationService = new InformationServiceImpl(informationRepository, userLocal, informationMapper, userService);
    }


    @Test
    void whenUpdateInformation_thenReturnNewInformation(){
        String userName = "Khoi";
        Users users = mock(Users.class);
        ModifyUserRequestDTO modifyUserRequestDTO = mock(ModifyUserRequestDTO.class);
        Information information = mock(Information.class);
        Information savedInformation = mock(Information.class);

        when(userLocal.getLocalUserName()).thenReturn(userName);
        when(userService.findByUserName(userName)).thenReturn(users);
        when(informationMapper.toExistedInformation(modifyUserRequestDTO, users.getInformation())).thenReturn(information);
        when(informationRepository.save(information)).thenReturn(savedInformation);

        assertThat(informationService.update(modifyUserRequestDTO), is(savedInformation));
    }
}
