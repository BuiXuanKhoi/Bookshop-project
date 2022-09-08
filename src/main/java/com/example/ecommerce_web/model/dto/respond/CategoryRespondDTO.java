package com.example.ecommerce_web.model.dto.respond;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRespondDTO {
    private int categoryId;

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("description")
    private String categoryDescription;
}
