package com.example.ecommerce_web.model.entities;

import com.example.ecommerce_web.validator.UniqueField;
import com.example.ecommerce_web.validator.Validator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int categoryId;

    @UniqueField(entity = Validator.CATEGORY, message = "Category Already Exist")
    @Column(name = "category_name", unique = true)
    @NotNull(message = "category name must not be null")
    @NotEmpty(message = "category name is required")
    private String categoryName;

    @Column(name = "category_description")
    @NotNull(message = "category description must not be null")
    @NotEmpty(message = "category description is required")
    private String categoryDescription;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Classify> classifies;

    public Category(String categoryName, String categoryDescription){
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
    }
}
