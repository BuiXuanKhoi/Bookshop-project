package com.example.ecommerce_web.model.dto.respond;

import com.example.ecommerce_web.constant.BookState;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookFeatureRespondDTO {

    private int bookId;

    private String bookName;

    private float bookPrice;

    private float ratingPoint;

    private String imageLink;

    private String authorName;

    private BookState bookState;
}
