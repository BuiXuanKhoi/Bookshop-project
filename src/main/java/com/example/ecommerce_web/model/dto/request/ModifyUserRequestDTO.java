package com.example.ecommerce_web.model.dto.request;

import lombok.*;

import javax.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyUserRequestDTO {


    @NotNull(message = "date of birth is required")
    private Date dateOfBirth;

    @Pattern(regexp = "[A-Za-z]+", message = "First name cannot be number or special characters")
    @NotNull(message = "First name cannot be null")
    private String firstName;

    @Pattern(regexp = "[A-Za-z]+", message = "Last name cannot be number or special characters")
    @NotNull(message = "Last name is required")
    @NotEmpty(message = "Last name must not be empty")
    private String lastName;

    @NotNull(message = "Address is required")
    @NotEmpty(message = "Address must not be empty")
    private String address;

    @Size(min = 11, max = 11, message = "Phone number must has at least 11 characters and no more")
    @NotNull(message = "phone number is required")
    @NotEmpty(message = "phone number must not be empty")
    private String phoneNumber;

//    @Pattern(regexp = "[A-Za-z0-9]+@[a-zA-Z0-9]{6}", message = "Invalid Email Address")
    @Email(message = "Invalid Email Address")
    @NotNull(message = "email is required")
    @NotEmpty(message = "email must not be empty")
    private String email;
}
