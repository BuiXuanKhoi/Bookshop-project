package com.example.ecommerce_web.model.dto.respond;

import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.model.entities.CartItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.awt.print.Book;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRespondDTO {

    @NotNull(message = "cart Item ID cannot be null")
    private int cartItemsID ;

    @NotNull(message = "Quantity cannot be null")
    private int quantity;

    @Min(value = 1, message = "Product has to have at least 1.")
    private int bookQuantity;

    @NotNull(message = "Price cannot be null")
    private float price;

    @NotNull(message = "Book name cannot be null")
    private String bookName;
    private String imageLink;
}
