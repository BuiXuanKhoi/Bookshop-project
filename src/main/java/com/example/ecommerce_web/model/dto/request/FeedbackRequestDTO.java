package com.example.ecommerce_web.model.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackRequestDTO {

    @NotEmpty(message = "title is required")
    @NotNull(message = "title must not be null")
    private String title;

    @NotEmpty(message = "comment is required")
    @NotNull(message = "comment must not be null")
    private String comment;

    @Min(value = 1, message = "rating point is required")
    @NotNull(message = "rating point must not be null")
    private float ratingPoint;
}
