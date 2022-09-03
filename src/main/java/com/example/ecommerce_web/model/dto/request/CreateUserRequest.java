package com.example.ecommerce_web.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    private String userName;

    private String password;

    private String phone;

    private String address;

    private Date dateOfBirth;

    private String firstName;

    private String lastName;

    private String email;
}
