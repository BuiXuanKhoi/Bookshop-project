package com.example.ecommerce_web.model.dto.respond;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtErrorRespond {

    private int statusCode;
    private String message;
    private String detail;
}
