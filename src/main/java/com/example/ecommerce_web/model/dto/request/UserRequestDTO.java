package com.example.ecommerce_web.model.dto.request;

import com.example.ecommerce_web.model.Location;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    @NotNull(message = "username cannot be null")
    @NotBlank(message = "username cannot be blank")
    @NotEmpty(message = "username cannot be empty")
    private String userName;

    @NotNull(message = "phone number cannot be null")
    @NotBlank(message = "phone number cannot be blank")
    private String phoneNumber;

    @NotNull(message = "address cannot be null")
    @NotBlank(message = "address cannot be blank")
    private String address;

    @NotNull(message = "date of birth cannot be null")
    private Date dateOfBirth;

    @NotNull(message = "first name cannot be null")
    @NotBlank(message = "first name cannot be blank")
    private String firstName;

    @NotNull(message = "last name cannot be null")
    @NotBlank(message = "last name cannot be blank")
    private String lastName;

    @Email(message = "Email Not Valid !!!")
    @NotNull(message = "email cannot be null")
    @NotBlank(message = "email cannot be blank")
    private String email;

    @NotNull(message = "role cannot be null")
    @NotBlank(message = "role cannot be blank")
    private String role;



}
