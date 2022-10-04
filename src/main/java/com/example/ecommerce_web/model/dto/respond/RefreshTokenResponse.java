package com.example.ecommerce_web.model.dto.respond;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenResponse {

    private String jwtToken;
    private String refreshToken;
    private String tokenType;
}
