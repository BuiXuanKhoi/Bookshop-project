package com.example.ecommerce_web.model.dto.respond;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackRespondDTO {

    private String userName;
    private Date CreateDay;
    private String comment;
    private float ratingPoint;
}
