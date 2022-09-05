package com.example.ecommerce_web.service.impl;


import com.example.ecommerce_web.exceptions.ConstraintViolateException;
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
import com.example.ecommerce_web.security.jwt.JwtUtils;
import com.example.ecommerce_web.security.service.UserDetail;
import com.example.ecommerce_web.service.AuthService;
import com.example.ecommerce_web.utils.MyDateUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    InformationRepository informationRepository;
    MyDateUtil myDateUtil;
    ModelMapper modelMapper;
    AuthenticationManager authenticationManager;
    PasswordEncoder encoder;
    JwtUtils jwtUtils;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           InformationRepository informationRepository, MyDateUtil myDateUtil,
                           ModelMapper modelMapper, JwtUtils jwtUtils, PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.informationRepository = informationRepository;
        this.myDateUtil = myDateUtil;
        this.modelMapper = modelMapper;
        this.jwtUtils = jwtUtils;
        this.encoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public LoginRespondDTO login(LoginRequestDTO loginRequestDTO) {

        Users users = this.userRepository.findUserByUserName(loginRequestDTO.getUserName()).get();

        if(!loginRequestDTO.getUserName().equals(users.getUserName())){
            throw new ResourceNotFoundException("Account Not Exist !!!!");
        }

        if (!encoder.matches(loginRequestDTO.getPassword(), users.getPassword())){
            throw new ResourceNotFoundException("Wrong password !!!");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUserName(), loginRequestDTO.getPassword())
        );


        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken(authentication);

        UserDetail userDetail = (UserDetail) authentication.getPrincipal();


        if(!encoder.matches(loginRequestDTO.getPassword(), userDetail.getPassword())){
            throw new ResourceNotFoundException("Password incorrect. Please try again !!!");
        }


        String roleName = userDetail.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList()).get(0);

        return new LoginRespondDTO(
                userDetail.getUserId(),
                userDetail.getUsername(),
                roleName,
                jwt,
                "Bearer"
        );
    }

    @Override
    public ResponseEntity<?> signup(CreateUserRequest createUserRequest) {
        Users users = createUser(createUserRequest);
        Information information = createInformation(createUserRequest, users);
        this.userRepository.save(users);
        this.informationRepository.save(information);
        return ResponseEntity.ok(new MessageRespond(HttpStatus.CREATED.value(), "Create Account Successfully !!!!"));
    }


    public Users createUser(CreateUserRequest createUserRequest){
        String roleName = createUserRequest.getRole();
        Date dateOfBirth = createUserRequest.getDateOfBirth();
        String userName = createUserRequest.getUserName();
        String password = generatePassword(userName, dateOfBirth);
        Role role = roleRepository.getRoleByRoleName(roleName);
        Date lockTime = new Date();

        Optional<Users> usersOptional = this.userRepository.findUserByUserName(userName);

        if(usersOptional.isPresent()){
            throw new ConstraintViolateException("User name already exist !!!!");
        }
        return new Users(userName, password, role, UserState.UNBLOCK, lockTime);
    }

    public Information createInformation(CreateUserRequest createUserRequest, Users users){
        Information information = modelMapper.map(createUserRequest, Information.class);
        information.setCreateDate(new Date());
        information.setUsers(users);
        return information;
    }

    public String generatePassword(String userName, Date dateOfBirth){
        String birth = myDateUtil.getStringDate(dateOfBirth);
        birth = birth.replaceAll("/", "");

        return encoder.encode(userName + "@" + birth);
    }
}
