package com.example.ecommerce_web.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRequestDTO {

    @NotEmpty(message = "quantity is required")
    private int quantity;

    @NotEmpty(message = "book id is required")
    private int bookId;
}
