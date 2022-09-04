package com.example.ecommerce_web.model.dto.respond;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRespondDTO {

    private String token;
    private String tokenType;
    private String userName;
    private String role;
}
