package com.example.ecommerce_web.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    private String userName;

//    @Pattern(regexp = "0[0-9]{10}", message = "Password must be number, start with 0 and 10 numbers")
    private String phoneNumber;

    private String address;

    private Date dateOfBirth;

    private String firstName;

    private String lastName;

    private String email;

    private String role;



}
