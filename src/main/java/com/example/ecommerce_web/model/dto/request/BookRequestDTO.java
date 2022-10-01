package com.example.ecommerce_web.model.dto.request;

import lombok.*;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDTO {

    private String bookName;


    @Min(value = 1, message = "Book Price must have at least 1.")
    private float bookPrice;


    private String imageLink;


    private String description;


    @Min(value = 1, message = "Quantity must have at least 1. ")
    private int quantity;

    @NotEmpty(message = "list category is required")
    @NotNull(message = "list category must not be null")
    private int[] listCategory;

    private int authorId;


    private int creator;


    private String state;
}
