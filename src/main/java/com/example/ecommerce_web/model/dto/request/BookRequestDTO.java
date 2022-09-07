package com.example.ecommerce_web.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.util.List;

@Data
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

}
