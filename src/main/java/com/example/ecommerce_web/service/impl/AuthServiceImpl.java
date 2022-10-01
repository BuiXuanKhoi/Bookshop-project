package com.example.ecommerce_web.service.impl;


import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.dto.request.UserRequestDTO;
import com.example.ecommerce_web.model.dto.request.EmailDetail;
import com.example.ecommerce_web.model.dto.request.LoginRequestDTO;
import com.example.ecommerce_web.model.dto.respond.LoginRespondDTO;
import com.example.ecommerce_web.model.dto.respond.MessageRespond;
import com.example.ecommerce_web.model.entities.Information;
import com.example.ecommerce_web.model.entities.Users;
import com.example.ecommerce_web.repository.InformationRepository;
import com.example.ecommerce_web.repository.UserRepository;
import com.example.ecommerce_web.security.jwt.JwtUtils;
import com.example.ecommerce_web.security.service.UserDetail;
import com.example.ecommerce_web.service.AuthService;
import com.example.ecommerce_web.service.EmailService;
import com.example.ecommerce_web.service.InformationService;
import com.example.ecommerce_web.service.UserService;
import com.example.ecommerce_web.utils.MyDateUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    UserRepository userRepository;
    InformationRepository informationRepository;
    MyDateUtil myDateUtil;
    ModelMapper modelMapper;
    AuthenticationManager authenticationManager;
    PasswordEncoder encoder;
    JwtUtils jwtUtils;
    UserService userService;
    InformationService informationService;


    @Autowired
    public AuthServiceImpl(UserRepository userRepository,
                           InformationRepository informationRepository, MyDateUtil myDateUtil,
                           ModelMapper modelMapper, JwtUtils jwtUtils, PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           UserService userService, InformationService informationService
                           ) {
        this.userRepository = userRepository;
        this.informationRepository = informationRepository;
        this.myDateUtil = myDateUtil;
        this.modelMapper = modelMapper;
        this.jwtUtils = jwtUtils;
        this.encoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.informationService = informationService;
    }

    @Override
    public LoginRespondDTO login(LoginRequestDTO loginRequestDTO) {

        Users users = userService.findByUserName(loginRequestDTO.getUserName());
        String loginPassword = loginRequestDTO.getPassword();
        String password = users.getPassword();
        Optional<Date> lockTime = Optional.ofNullable(users.getLockTime());
        Date now = new Date();

        if (!encoder.matches(loginPassword, password)){
            throw new ResourceNotFoundException("Wrong password !!!");
        }

        if (lockTime.isPresent()){
            if (lockTime.get().before(now)){
                userService.unblock(users.getUserId());
            }
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUserName(), loginRequestDTO.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken(authentication);
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();

        String roleName = userDetail.getAuthorities().stream()
                                                     .map(GrantedAuthority::getAuthority)
                                                     .collect(Collectors.toList()).get(0);

        return LoginRespondDTO.builder()
                              .role(roleName)
                              .token(jwt)
                              .userId(userDetail.getUserId())
                              .userName(userDetail.getUsername())
                              .tokenType("Bearer")
                              .build();
    }

    @Override
    public ResponseEntity<?> signup(UserRequestDTO userRequestDTO) {
        Users users = userService.add(userRequestDTO);
        Information information = informationService.createInformationByExistedUser(userRequestDTO, users);
        String password = users.getPassword();
        System.out.println(password);
        String passwordEncode = encoder.encode(password);
        users.setPassword(passwordEncode);
        this.userRepository.save(users);
        this.informationRepository.save(information);
        String receiver = information.getEmail();
//        emailService.sendEmail(password, receiver);
        return ResponseEntity.ok(new MessageRespond(HttpStatus.CREATED.value(), "Create Account Successfully !!!!"));
    }


}
