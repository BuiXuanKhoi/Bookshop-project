package com.example.ecommerce_web.service.impl;


import com.example.ecommerce_web.exceptions.ConstraintViolateException;
import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.Location;
import com.example.ecommerce_web.model.UserState;
import com.example.ecommerce_web.model.dto.request.UserRequestDTO;
import com.example.ecommerce_web.model.dto.request.EmailDetail;
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
import com.example.ecommerce_web.service.EmailService;
import com.example.ecommerce_web.utils.MyDateUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    EmailService emailService;

    String password = "";

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           InformationRepository informationRepository, MyDateUtil myDateUtil,
                           ModelMapper modelMapper, JwtUtils jwtUtils, PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager, @Qualifier("googleEmail") EmailService emailService
                           ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.informationRepository = informationRepository;
        this.myDateUtil = myDateUtil;
        this.modelMapper = modelMapper;
        this.jwtUtils = jwtUtils;
        this.encoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
    }

    @Override
    public LoginRespondDTO login(LoginRequestDTO loginRequestDTO) {

        Users users = this.userRepository.findUserByUserName(loginRequestDTO.getUserName()).get();
        String loginUserName = loginRequestDTO.getUserName();
        String userName = users.getUserName();
        String loginPassword = loginRequestDTO.getPassword();
        String password = users.getPassword();

        if(!loginUserName.equals(userName)){
            throw new ResourceNotFoundException("Account Not Exist !!!!");
        }

        if (!encoder.matches(loginPassword, password)){
            throw new ResourceNotFoundException("Wrong password !!!");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUserName(), loginRequestDTO.getPassword())
        );


        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken(authentication);

        UserDetail userDetail = (UserDetail) authentication.getPrincipal();


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
    public ResponseEntity<?> signup(UserRequestDTO userRequestDTO) {
        Users users = createUser(userRequestDTO);
        Information information = createInformation(userRequestDTO, users);
        String receiver = information.getEmail();
        this.userRepository.save(users);
        this.informationRepository.save(information);

        sendEmail(receiver);

        return ResponseEntity.ok(new MessageRespond(HttpStatus.CREATED.value(), "Create Account Successfully !!!!"));
    }

    private void sendEmail(String receiver){
        String message = "Thanks for join our E-commerce project. Your password here: " +  password +
                ". Please change your password after login.";
        String subject = "Welcome Join Us !";
        EmailDetail emailDetail = new EmailDetail(receiver, message, subject);
        emailService.sendEmail(emailDetail);
    }


    private Users createUser(UserRequestDTO userRequestDTO){
        String roleName = userRequestDTO.getRole();
        Date dateOfBirth = userRequestDTO.getDateOfBirth();
        String userName = userRequestDTO.getUserName();
        String password = generatePassword(userName, dateOfBirth);
        Role role = roleRepository.getRoleByRoleName(roleName);
        Date lockTime = new Date();

        Optional<Users> usersOptional = this.userRepository.findUserByUserName(userName);

        if(usersOptional.isPresent()){
            throw new ConstraintViolateException("User name already exist !!!!");
        }
        return new Users(userName, password, role, UserState.UNBLOCK, lockTime);
    }

    private Information createInformation(UserRequestDTO userRequestDTO, Users users){
        Information information = modelMapper.map(userRequestDTO, Information.class);
        information.setCreateDate(new Date());
        information.setUsers(users);

        String location = userRequestDTO.getAddress();
        Location address = Location.getLocation(location);
        information.setAddress(address);

        return information;
    }

    private String generatePassword(String userName, Date dateOfBirth){
        String birth = myDateUtil.getStringDate(dateOfBirth);
        birth = birth.replaceAll("/", "");
        password = userName + "@" + birth;
        return encoder.encode(password);
    }
}
