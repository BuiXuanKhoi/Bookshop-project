package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.ConstraintViolateException;
import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.constant.Role;
import com.example.ecommerce_web.constant.UserState;
import com.example.ecommerce_web.model.dto.request.ChangePasswordRequestDTO;
import com.example.ecommerce_web.model.dto.request.UserRequestDTO;
import com.example.ecommerce_web.model.dto.respond.MessageRespond;
import com.example.ecommerce_web.model.dto.respond.UserRespondDTO;
import com.example.ecommerce_web.model.entities.Users;
import com.example.ecommerce_web.repository.UserRepository;
import com.example.ecommerce_web.security.service.UserLocal;
import com.example.ecommerce_web.service.UserService;
import com.example.ecommerce_web.utils.MyDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MyDateUtil myDateUtil;
    private final PasswordEncoder encoder;
    private final UserLocal userLocal;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, MyDateUtil myDateUtil,
                           PasswordEncoder encoder,
                           UserLocal userLocal
                           ) {
        this.userRepository = userRepository;
        this.myDateUtil = myDateUtil;
        this.encoder = encoder;
        this.userLocal = userLocal;
    }

    public Users findById(int id){
        return this.userRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Not Found User With ID: " + id)
                );
    }


    @Override
    public Users findByUserName(String userName) {
        return this.userRepository.findByUserName(userName)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Not Found User With Name: " + userName));
    }

    @Override
    public Users findLocalUser() {
        String userName = userLocal.getLocalUserName();
        return findByUserName(userName);
    }

    @Override
    public void deleteAll() {
        this.userRepository.deleteAll();
    }

    @Override
    public ResponseEntity<?> changePassword(ChangePasswordRequestDTO changePasswordRequestDTO){

        String newPassword = changePasswordRequestDTO.getNewPassword();
        String oldPassword = changePasswordRequestDTO.getOldPassword();
        String userName = userLocal.getLocalUserName();
        Users users = findByUserName(userName);
        String validPassword = users.getPassword();

        if(!encoder.matches(oldPassword,validPassword)) {
           throw new ResourceNotFoundException("Password is wrong !!!!");
        }

        newPassword = encoder.encode(newPassword);
        users.setPassword(newPassword);
        this.userRepository.save(users);

        return ResponseEntity.ok(new MessageRespond(HttpStatus.OK.value(), "Your password has been updated !!!"));
    }

    @Override
    public ResponseEntity<?> block(int userId) {
        long THREE_MINUTES = 180000;
        // Because of fast testing, You just have to waiting for 3 minutes and log in again, the account will be available again.

        Users users = findById(userId);

        Date now = new Date();
        long lockUntil = now.getTime() + THREE_MINUTES;

        users.setUserState(UserState.BLOCK);
        users.setLockTime(new Date(lockUntil));
        this.userRepository.save(users);

        MessageRespond messageRespond = new MessageRespond( HttpStatus.OK.value(),"Block User Successfully !!!!");
        return ResponseEntity.ok(messageRespond);
    }

    @Override
    public ResponseEntity<?> unblock(int userID){

        Users users = findById(userID);

        users.setUserState(UserState.UNBLOCK);
        users.setLockTime(null);

        this.userRepository.save(users);

        return ResponseEntity.ok(new MessageRespond(HttpStatus.OK.value(), "User has been unblocked !!!"));
    }

    @Override
    public Page<UserRespondDTO> getPage(int page){

        Pageable pageable = PageRequest.of(page,10);
        Page<UserRespondDTO> userList = this.userRepository.getPage(pageable);

        if(userList.hasContent()){
            return userList;
        }
        throw new ResourceNotFoundException("Not found user !!!");
    }

    @Override
    public Users add(UserRequestDTO userRequestDTO) {
        Date dateOfBirth = userRequestDTO.getDateOfBirth();
        String userName = userRequestDTO.getUserName();
        String roleName = userRequestDTO.getRole();
        String password = generatePassword(userName, dateOfBirth);
        Role role = Role.getRole(roleName);

        Optional<Users> usersOptional = this.userRepository.findByUserName(userName);

        if(usersOptional.isPresent()){
            throw new ConstraintViolateException("User name already exist !!!!");
        }
        
        return Users.builder()
                    .userName(userName)
                    .password(password)
                    .role(role)
                    .userState(UserState.UNBLOCK)
                    .build();
    }


    private String generatePassword(String userName, Date dateOfBirth){
        String birth = myDateUtil.getStringDate(dateOfBirth);
        birth = birth.replaceAll("/", "");
        return userName + "@" + birth;
    }
}
