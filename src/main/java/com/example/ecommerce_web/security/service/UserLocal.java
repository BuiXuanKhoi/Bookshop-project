package com.example.ecommerce_web.security.service;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserLocal {


    public String getLocalUserName(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        if(userName == null){
            throw new ResourceNotFoundException("You haven't Login !!!");
        }

        return userName;
    }

}
