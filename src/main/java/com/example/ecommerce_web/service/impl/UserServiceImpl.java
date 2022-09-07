package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.ApiDeniedException;
import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.UserState;
import com.example.ecommerce_web.model.dto.request.ChangePasswordRequestDTO;
import com.example.ecommerce_web.model.dto.respond.MessageRespond;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

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

        String userName = userLocal.getLocalUserName();

        Optional<Users> users= this.userRepository.findUserByUserName(userName);

        Users optional_users = users.get();


        if(!encoder.matches(changePasswordRequestDTO.getOldPassword(),optional_users.getPassword())) {
           throw new ResourceNotFoundException("Password is wrong !!!!");
        }
        else {
                newPassword = encoder.encode(newPassword);
                optional_users.setPassword(newPassword);
                this.userRepository.save(optional_users);
        }
        return ResponseEntity.ok(new MessageRespond(HttpStatus.OK.value(), "Your password has been updated !!!"));
    }

    @Override
    public ResponseEntity<?> blockUser(int userId) {
        long THREE_DAY = 259200000;
        Optional<Users> usersOptional = this.userRepository.findById(userId);

        usersOptional.orElseThrow(
                () -> new ResourceNotFoundException("Not Found User With ID: " + userId)
        );

        Users users = usersOptional.get();

        if(users.getRole().getRoleName().equals("ADMIN")){
            throw new ApiDeniedException("Only admin can block users !!!");
        }

        Date now = new Date();

        long lockUntil = now.getTime() + THREE_DAY;

        users.setUserState(UserState.BLOCK);
        users.setLockTime(new Date(lockUntil));

        this.userRepository.save(users);

        MessageRespond messageRespond = new MessageRespond( HttpStatus.OK.value(),"Block User Successfully !!!!");
        return ResponseEntity.ok(messageRespond);
    }


}
