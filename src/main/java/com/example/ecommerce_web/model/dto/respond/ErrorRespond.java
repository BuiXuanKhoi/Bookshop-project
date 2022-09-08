package com.example.ecommerce_web.model.dto.respond;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorRespond {

    private int statusCode;
    private String message;
    private Map<String, String> validateMessage;

    public ErrorRespond(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
