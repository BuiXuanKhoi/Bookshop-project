package com.example.ecommerce_web.model.dto.respond;


import com.example.ecommerce_web.model.BookState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRespondDTO {

    private String bookName;
    private String description;
    private float bookPrice;
    private int quantity;
    private String authorName;
    private List<String> categoryName;
    private float ratingPoint;
    private String review;
    private String imageLink;
    private Date createDay;
    private Date updateDay;
    private BookState bookState;
}
