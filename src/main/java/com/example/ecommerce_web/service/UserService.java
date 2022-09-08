package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.dto.request.ChangePasswordRequestDTO;
import com.example.ecommerce_web.model.dto.respond.UserRespondDTO;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;

import java.util.List;


public interface UserService {
    ResponseEntity<?> changePassword(ChangePasswordRequestDTO changePasswordRequestDTO);

    ResponseEntity<?> blockUser(int userId);

    ResponseEntity<?> unblockUser(int userId);

    Page<UserRespondDTO> getUserList(int page);
}
