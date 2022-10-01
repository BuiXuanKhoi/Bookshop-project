package com.example.ecommerce_web.model.dto.respond;

import com.example.ecommerce_web.model.entities.Feedback;
import com.example.ecommerce_web.model.entities.Users;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackRespondDTO {

    private String userName;
    private Date CreateDay;
    private String comment;
    private float ratingPoint;
}
