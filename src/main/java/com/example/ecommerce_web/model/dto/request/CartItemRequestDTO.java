package com.example.ecommerce_web.model.dto.request;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemRequestDTO {

    @Min(value = 1, message = "Quantity is required")
    @NotNull(message = "quantity must not be null")
    private int quantity;

    @Min(value = 1, message = "Book id is required")
    @NotNull(message = " id must not be null")
    private int bookId;
}
