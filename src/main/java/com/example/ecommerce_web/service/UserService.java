package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.dto.request.ChangePasswordRequestDTO;
import org.springframework.http.ResponseEntity;


public interface UserService {
    ResponseEntity<?> changePassword(ChangePasswordRequestDTO changePasswordRequestDTO);

    ResponseEntity<?> blockUser(int userId);
}
