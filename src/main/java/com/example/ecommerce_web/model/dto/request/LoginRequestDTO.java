package com.example.ecommerce_web.model.dto.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    @NotNull(message = "username is required")
    @NotEmpty(message = "username must not be empty")
    private String userName;

    @NotEmpty(message = "password must not be empty")
    @NotNull(message = "password is required")
    private String password;
}
