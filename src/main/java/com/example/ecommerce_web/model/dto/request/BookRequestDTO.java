package com.example.ecommerce_web.model.dto.request;

import lombok.*;

import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDTO {

    @Pattern(regexp = "[A-Za-z]+")
    private String bookName;

    private float bookPrice;

    private String imageLink;

    private String description;

    private int quantity;

    private int[] listCategory;

    private int authorId;

    private int creator;

    private String state;
}