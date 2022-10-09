package com.example.ecommerce_web.model.dto.respond;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RateCountingRespondDTO {

    private int wonderful;
    private int good;
    private int normal;
    private int bad;
    private int terrible;

}
