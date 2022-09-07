package com.example.ecommerce_web.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequestDTO {


    private String oldPassword;
    private String newPassword;
    private String newPasswordRetype;
}