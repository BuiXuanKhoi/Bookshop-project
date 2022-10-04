package com.example.ecommerce_web.model.dto.respond;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRespondDTO {

    private int userId;
    private String userName;
    private String role;
    private String tokenType;
    private String token;
    private String refreshToken;
    private Date expires;



}
