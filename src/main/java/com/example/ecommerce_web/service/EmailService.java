package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.dto.request.EmailDetail;
import org.springframework.http.ResponseEntity;

public interface EmailService {
    ResponseEntity<?> sendWelcomeEmail(String password, String receiver);
}
