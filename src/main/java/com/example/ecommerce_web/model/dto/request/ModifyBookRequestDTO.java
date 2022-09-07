package com.example.ecommerce_web.model.dto.request;

import com.example.ecommerce_web.model.BookState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyBookRequestDTO {

    private String bookName;

    private float bookPrice;

    private int quantity;

    private String description;

    private String state;
}
