package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.dto.request.EmailDetail;
import com.example.ecommerce_web.model.dto.respond.MessageRespond;
import com.example.ecommerce_web.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
//
//@Service
//@Qualifier("googleEmail")
public class GoogleEmailServiceImpl implements EmailService {

//    @Value("${spring.mail.username}")
//    private String sender;

//    private JavaMailSender javaMailSender;
//
//    @Autowired
//    public GoogleEmailServiceImpl(JavaMailSender javaMailSender) {
//        this.javaMailSender = javaMailSender;
//    }

    @Override
    public ResponseEntity<?> sendWelcomeEmail(String password, String receiver) {
//            SimpleMailMessage mailMessage = new SimpleMailMessage();
//
//            String message = "Thanks for join our E-commerce project. Your password here: " +  password +
//                ". Please change your password after login.";
//            String subject = "Welcome Join Us !";
//
//            mailMessage.setSubject(subject);
//            mailMessage.setTo(receiver);
//            mailMessage.setFrom(sender);
//            mailMessage.setText(message);
//            javaMailSender.send(mailMessage);

            return ResponseEntity.ok(new MessageRespond(HttpStatus.OK.value(), "Email Sent Success !!!"));


    }
}
