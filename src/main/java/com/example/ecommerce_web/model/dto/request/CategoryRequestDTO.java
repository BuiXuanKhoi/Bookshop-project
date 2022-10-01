package com.example.ecommerce_web.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.implementation.bind.annotation.BindingPriority;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDTO {


    @NotEmpty(message = "category name is required")
    private String categoryName;

    @NotEmpty(message = "description is required")
    private String categoryDescription;
}
