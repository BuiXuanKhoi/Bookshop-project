package com.example.ecommerce_web.model.dto.respond;

import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.model.entities.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.awt.print.Book;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRespondDTO {

    @NotNull(message = "cart Item ID cannot be null")
    private int cartItemsID ;

    @NotNull(message = "Quantity cannot be null")
    private int quantity;

    @NotNull(message = "Price cannot be null")
    private float price;

    @NotNull(message = "Book name cannot be null")
    private String bookName;
    private String imageLink;


    public CartItemRespondDTO(CartItem cartItem){
        setCartItemsID(cartItem.getCartItemsID());
        setQuantity(cartItem.getQuantity());
        Books book = cartItem.getBooks();
        setBookName(book.getBookName());
        setImageLink(book.getImageLink());
        setPrice(book.getBookPrice());
    }

}
