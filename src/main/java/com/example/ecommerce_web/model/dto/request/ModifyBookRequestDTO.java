package com.example.ecommerce_web.model.dto.request;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyBookRequestDTO {

    @NotEmpty(message = "book name is required")
    @NotNull(message = "book name must not be null")
    private String bookName;

    @NotEmpty(message = "book price is required")
    @NotNull(message = "book price must not be null")
    @Min(value = 1, message = "Book Price must have at least 1.")
    private float bookPrice;


    @NotEmpty(message = "quantity is required")
    @NotNull(message = "quantity must not be null")
    @Min(value = 1, message = "Quantity must have at least 1. ")
    private int quantity;

    @NotEmpty(message = "description is required")
    @NotNull(message = "description must not be null")
    private String description;

    @NotEmpty(message = "state is required")
    @NotNull(message = "state must not be null")
    private String state;
}
