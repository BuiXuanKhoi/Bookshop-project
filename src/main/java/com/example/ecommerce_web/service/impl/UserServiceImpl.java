package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.UserState;
import com.example.ecommerce_web.model.dto.request.ChangePasswordRequestDTO;
import com.example.ecommerce_web.model.dto.respond.MessageRespond;
import com.example.ecommerce_web.model.dto.respond.UserRespondDTO;
import com.example.ecommerce_web.model.entities.Information;
import com.example.ecommerce_web.model.entities.Users;
import com.example.ecommerce_web.repository.UserRepository;
import com.example.ecommerce_web.security.jwt.JwtUtils;
import com.example.ecommerce_web.security.service.UserLocal;
import com.example.ecommerce_web.service.EmailService;
import com.example.ecommerce_web.service.UserService;
import com.example.ecommerce_web.utils.MyDateUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    MyDateUtil myDateUtil;
    ModelMapper modelMapper;
    AuthenticationManager authenticationManager;
    PasswordEncoder encoder;
    JwtUtils jwtUtils;
    UserLocal userLocal;
    EmailService emailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, MyDateUtil myDateUtil, ModelMapper modelMapper,
                           AuthenticationManager authenticationManager, PasswordEncoder encoder, JwtUtils jwtUtils,
                           UserLocal userLocal, @Qualifier("googleEmail") EmailService emailService
                           ) {
        this.userRepository = userRepository;
        this.myDateUtil = myDateUtil;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
        this.userLocal = userLocal;
        this.emailService = emailService;
    }

    @Override
    public ResponseEntity<?> changePassword(ChangePasswordRequestDTO changePasswordRequestDTO){

        String newPassword = changePasswordRequestDTO.getNewPassword();
        String oldPassword = changePasswordRequestDTO.getOldPassword();

        String userName = userLocal.getLocalUserName();

        Optional<Users> users= this.userRepository.findUserByUserName(userName);

        Users optionalUsers = users.get();
        String validPassword = optionalUsers.getPassword();


        if(!encoder.matches(oldPassword,validPassword)) {
           throw new ResourceNotFoundException("Password is wrong !!!!");
        }

        newPassword = encoder.encode(newPassword);
        optionalUsers.setPassword(newPassword);
        this.userRepository.save(optionalUsers);

        return ResponseEntity.ok(new MessageRespond(HttpStatus.OK.value(), "Your password has been updated !!!"));
    }

    @Override
    public ResponseEntity<?> blockUser(int userId) {
        long THREE_MINUTES = 180000; // You can waiting for 3 minutes and log in again, the account will be available.
        Optional<Users> usersOptional = this.userRepository.findById(userId);

        usersOptional.orElseThrow(
                () -> new ResourceNotFoundException("Not Found User With ID: " + userId)
        );

        Users users = usersOptional.get();

        Date now = new Date();

        long lockUntil = now.getTime() + THREE_MINUTES;

        users.setUserState(UserState.BLOCK);
        users.setLockTime(new Date(lockUntil));

        this.userRepository.save(users);

        MessageRespond messageRespond = new MessageRespond( HttpStatus.OK.value(),"Block User Successfully !!!!");
        return ResponseEntity.ok(messageRespond);
    }

    @Override
    public ResponseEntity<?> unblockUser(int userID){


        Optional<Users> usersOptional = this.userRepository.findById(userID);

        usersOptional.orElseThrow(
                () -> new ResourceNotFoundException("Not Found User with ID:" + userID)
        );

        Users users = usersOptional.get();

        users.setUserState(UserState.UNBLOCK);
        users.setLockTime(null);

        this.userRepository.save(users);

        return ResponseEntity.ok(new MessageRespond(HttpStatus.OK.value(), "User has been unblocked !!!"));
    }

    // ---------------------------------------- Get user list -----------------------------------------------------
    @Override
    public Page<UserRespondDTO> getUserList(int page){

        Pageable pageable = PageRequest.of(page,10);

        Page<UserRespondDTO> userList = this.userRepository.getPageUser(pageable);

        if(userList.hasContent()){
            return userList;
        }
        throw new ResourceNotFoundException("Not found user !!!");
    }
}
