package com.example.ecommerce_web.service.impl;


import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.UserState;
import com.example.ecommerce_web.model.dto.request.CreateUserRequest;
import com.example.ecommerce_web.model.dto.request.LoginRequestDTO;
import com.example.ecommerce_web.model.dto.respond.LoginRespondDTO;
import com.example.ecommerce_web.model.dto.respond.MessageRespond;
import com.example.ecommerce_web.model.entities.Information;
import com.example.ecommerce_web.model.entities.Role;
import com.example.ecommerce_web.model.entities.Users;
import com.example.ecommerce_web.repository.InformationRepository;
import com.example.ecommerce_web.repository.RoleRepository;
import com.example.ecommerce_web.repository.UserRepository;
import com.example.ecommerce_web.service.AuthService;
import com.example.ecommerce_web.utils.MyDateUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    InformationRepository informationRepository;
    MyDateUtil myDateUtil;
    ModelMapper modelMapper;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           InformationRepository informationRepository, MyDateUtil myDateUtil, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.informationRepository = informationRepository;
        this.myDateUtil = myDateUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public LoginRespondDTO login(LoginRequestDTO loginRequestDTO) {
        return null;
    }

    @Override
    public ResponseEntity<?> signup(CreateUserRequest createUserRequest) {
        Users users = createUser(createUserRequest);
        Information information = createInformation(createUserRequest, users);
        this.userRepository.save(users);
        this.informationRepository.save(information);
        return ResponseEntity.ok(new MessageRespond(HttpStatus.OK.value(), "Create Account Successfully !!!!"));
    }

    public Users createUser(CreateUserRequest createUserRequest){
        String roleName = createUserRequest.getRole();
        Date dateOfBirth = createUserRequest.getDateOfBirth();
        String userName = createUserRequest.getUserName();
        String password = generatePassword(userName, dateOfBirth);
        Role role = roleRepository.getRoleByRoleName(roleName);
        Date lockTime = new Date();
        return new Users(userName, password, role, UserState.UNBLOCK, lockTime);
    }

    public Information createInformation(CreateUserRequest createUserRequest, Users users){
        Information information = modelMapper.map(createUserRequest, Information.class);
        information.setCreateDate(new Date());
        information.setUsers(users);
        return information;
    }

    public String generatePassword(String userName, Date dateOfBirth){
        System.out.println(dateOfBirth);
        String birth = myDateUtil.getStringDate(dateOfBirth);
        birth = birth.replaceAll("/", "");

        return userName + "@" + birth;
    }
}
