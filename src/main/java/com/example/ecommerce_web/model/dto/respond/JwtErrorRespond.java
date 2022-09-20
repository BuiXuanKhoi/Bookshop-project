package com.example.ecommerce_web.model.dto.respond;

import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtErrorRespond {

    private int statusCode;
    private String message;
    private String detail;
}
