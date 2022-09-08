package com.example.ecommerce_web.model.dto.request;

import lombok.*;

import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequestDTO {

    @Min(value = 6, message = "Password must has at least 6 characters")
    private String oldPassword;

    @Min(value = 6, message = "Password must has at least 6 characters")
    private String newPassword;
}
