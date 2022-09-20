package com.example.ecommerce_web.model.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyBookRequestDTO {

    private String bookName;

    private float bookPrice;

    private int quantity;

    private String description;

    private String state;
}
