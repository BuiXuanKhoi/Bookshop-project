package com.example.ecommerce_web.model.dto.respond;


import com.example.ecommerce_web.constant.BookState;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRespondDTO {
    private int bookId;
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
