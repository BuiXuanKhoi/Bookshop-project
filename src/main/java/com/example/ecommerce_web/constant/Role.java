package com.example.ecommerce_web.constant;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;

import java.util.Arrays;

public enum Role {
    ADMIN("ADMIN"),
    CUSTOMER("CUSTOMER");

    private final String role;

    Role(String role) {
        this.role = role;
    }



    public static Role getRole(String roleName){
        return Arrays.stream(values())
                     .filter(role1 -> role1.role.equals(roleName))
                     .findAny()
                     .orElseThrow(() -> new ResourceNotFoundException("Role Not Available !!!"));

    }
}
