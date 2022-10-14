package com.example.ecommerce_web.model.dto.request;

import com.example.ecommerce_web.exceptions.ConstraintViolateException;
import lombok.*;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDTO {

    @NotEmpty(message = "book name is required")
    @NotNull(message = "book name must not be null")
    private String bookName;

    @NotNull(message = "book price must not be null")
    @Min(value = 1, message = "Book Price must have at least 1.")
    private float bookPrice;

    @NotEmpty(message = "description is required")
    @NotNull(message = "description must not be null")
    private String description;

    @NotNull(message = "quantity must not be null")
    @Min(value = 1, message = "Quantity must have at least 1. ")
    private int quantity;

    @NotEmpty(message = "list category is required")
    @NotNull(message = "list category must not be null")
    private int[] listCategory;

    @NotNull(message = "author must not be null")
    @Min(value = 1, message = "Author id is required")
    private int authorId;

    @NotEmpty(message = "state is required")
    @NotNull(message = "state must not be null")
    private String state;


}
