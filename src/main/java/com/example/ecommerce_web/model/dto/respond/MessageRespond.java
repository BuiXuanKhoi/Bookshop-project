package com.example.ecommerce_web.model.dto.respond;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageRespond {

    private int statusCode;
    private String message;
}
