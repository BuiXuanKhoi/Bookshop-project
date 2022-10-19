package com.example.ecommerce_web.controller;

import com.example.ecommerce_web.model.dto.request.ChangePasswordRequestDTO;
import com.example.ecommerce_web.model.dto.request.ModifyUserRequestDTO;
import com.example.ecommerce_web.model.dto.respond.InformationRespondDTO;
import com.example.ecommerce_web.model.dto.respond.UserRespondDTO;
import com.example.ecommerce_web.model.entities.Information;
import com.example.ecommerce_web.model.entities.Users;
import com.example.ecommerce_web.repository.InformationRepository;
import com.example.ecommerce_web.security.service.UserLocal;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.ecommerce_web.mapper.InformationMapper;
import com.example.ecommerce_web.service.InformationService;
import com.example.ecommerce_web.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.runner.RunWith;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import javax.swing.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc

public class UserControllerTest {

    UserService userService;
    InformationMapper informationMapper;
    InformationService informationService;
    UserLocal userLocal;
    InformationRepository informationRepository;
    Authentication authentication;
    SecurityContext securityContext;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @BeforeEach
    void init(){
        userService = mock(UserService.class);
        informationMapper = mock(InformationMapper.class);
        informationService = mock(InformationService.class);
        userLocal = mock(UserLocal.class);
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        informationRepository = mock(InformationRepository.class);
        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
    }

    @Test
    public void whenSendRequestToModifyUserInformation_returnUserWithNewInformation () throws Exception {

        String userName = "thanhnghi";
        InformationRespondDTO informationRespondDTO = mock(InformationRespondDTO.class);
        Information information = mock(Information.class);
        ObjectMapper objectMapper = new ObjectMapper();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(userName);
        SecurityContextHolder.setContext(securityContext);

        ModifyUserRequestDTO modifyUserRequestDTO =
                ModifyUserRequestDTO.builder()
                        .dateOfBirth(new Date())
                        .firstName("Martin")
                        .lastName("Charlie")
                        .address("HN")
                        .phoneNumber("0794562342")
                        .email("martinCharlie@gmail.com").build();

        when(informationService.update(modifyUserRequestDTO)).thenReturn(information);
        when(informationMapper.toDTO(information)).thenReturn(informationRespondDTO);

        mvc.perform(MockMvcRequestBuilders.put("/api/users/information")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modifyUserRequestDTO))
        )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void  whenSendRequestToModifyUserInformation_throwMethodArgumentNotValidException_ifOneFieldIsNull () throws Exception {
        String userName = "thanhnghi";
        InformationRespondDTO informationRespondDTO = mock(InformationRespondDTO.class);
        Information information = mock(Information.class);
        ObjectMapper objectMapper = new ObjectMapper();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(userName);
        SecurityContextHolder.setContext(securityContext);

        ModifyUserRequestDTO modifyUserRequestDTO = ModifyUserRequestDTO.builder()
                .dateOfBirth(null)
                .firstName("Martin")
                .lastName("Charlie")
                .address("HN")
                .phoneNumber("0794562342")
                .email("martinCharlie@gmail.com")
                .build();

        when(informationService.update(modifyUserRequestDTO)).thenReturn(information);
        when(informationMapper.toDTO(information)).thenReturn(informationRespondDTO);

        mvc.perform(MockMvcRequestBuilders.put("/api/users/information")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyUserRequestDTO))
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{'message':'Validate Error !!!','validateMessage':{'dateOfBirth':'date of birth is required'}}"))
                .andDo(print());
    }

    @Test
    public void whenSendRequestToChangePassword_returnNewPasswordAndSuccessInform () throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String userName = "khoiprovip";

        when(authentication.getName()).thenReturn(userName);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        ChangePasswordRequestDTO changePasswordRequestDTO = ChangePasswordRequestDTO.builder()
                                                                                    .oldPassword("khoi123456esc")
                                                                                    .newPassword("khoi12").build();

        mvc.perform(MockMvcRequestBuilders.put("/api/users/password")
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .content(objectMapper.writeValueAsString(changePasswordRequestDTO))
        )
                .andExpect(status().isOk())
                .andExpect(content().json("{'message':'Your password has been updated !!!'}"))
                .andDo(print());
    }

    @Test
    public void whenSendRequestToChangePassword_throwValidatedError_ifTheNumberOfCharInNewOrOldPasswordIsNotBetween6To30 () throws Exception {
        String userName = "khoi3";
        ObjectMapper objectMapper = new ObjectMapper();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(userName);
        SecurityContextHolder.setContext(securityContext);

        ChangePasswordRequestDTO changePasswordRequestDTO = ChangePasswordRequestDTO.builder()
                                                                                    .oldPassword("khoi3@01122000")
                                                                                    .newPassword("khoi")
                                                                                    .build();

        mvc.perform(MockMvcRequestBuilders.put("/api/users/password")
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .content(objectMapper.writeValueAsString(changePasswordRequestDTO))
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{'message':'Validate Error !!!','validateMessage':{'newPassword':'New Password must be around 6 and 15 characters'}}"))
                .andDo(print());
    }

    @Test
    public void whenSendRequestToChangePassword_throwValidatedError_ifNewPasswordOrOldPasswordIsNull () throws Exception {
        String userName = "khoi3";
        ObjectMapper objectMapper = new ObjectMapper();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(userName);
        SecurityContextHolder.setContext(securityContext);

        ChangePasswordRequestDTO changePasswordRequestDTO = ChangePasswordRequestDTO.builder()
                .oldPassword("khoi3@01122000")
                .newPassword("")
                .build();

        mvc.perform(MockMvcRequestBuilders.put("/api/users/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changePasswordRequestDTO))
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{'message':'Validate Error !!!','validateMessage':{'newPassword':'New Password must not be empty'}}"))
                .andDo(print());
    }

//    public void whenSendRequestToChangePassword_throwResourceNotFoundException_ifOldPasswordIsWrong ()  throws Exception {
//        Authentication authentication = mock(Authentication.class);
//
//    }
    @Test
    public void whenSendRequestToGetInformation_returnInformationDetail () throws Exception {
        InformationRespondDTO informationRespondDTO = mock(InformationRespondDTO.class);
        String userName = "khoiprovip";
        Information information = mock(Information.class);

        when(authentication.getName()).thenReturn(userName);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(informationService.getByLocalUser()).thenReturn(information);
        when(informationMapper.toDTO(information)).thenReturn(informationRespondDTO);

        mvc.perform(MockMvcRequestBuilders.get("/api/users/information"))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    public void whenSendRequestToGetInformation_throwUserNotFound_ifUserNameIsWrong () throws Exception {
        InformationRespondDTO informationRespondDTO = mock(InformationRespondDTO.class);
        String userName = "khoiip";
        Information information = mock(Information.class);

        when(authentication.getName()).thenReturn(userName);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(informationService.getByLocalUser()).thenReturn(information);
        when(informationMapper.toDTO(information)).thenReturn(informationRespondDTO);

        mvc.perform(MockMvcRequestBuilders.get("/api/users/information"))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{'message':'Not Found User With Name: khoiip'}"))
                .andDo(print());

    }
}
