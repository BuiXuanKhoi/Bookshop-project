package com.example.ecommerce_web.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequestDTO {

    @Min(value = 6, message = "Password must has at least 6 characters")
    private String oldPassword;

    @Min(value = 6, message = "Password must has at least 6 characters")
    private String newPassword;
}
