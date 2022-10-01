package com.example.ecommerce_web.model.dto.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequestDTO {

    @Length(min = 6, max = 15, message = "Password must be around 6 and 15 characters")
    @NotEmpty(message = "Old password is required")
    private String oldPassword;

    @Length(min = 6, max = 15, message = "New Password must be around 6 and 15 characters")
    @NotEmpty(message = "New Password is required")
    private String newPassword;
}
