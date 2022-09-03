package com.example.ecommerce_web.model.entities;

import lombok.Data;
import java.util.List;
import javax.persistence.*;

@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int category_id;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "category_description")
    private String categoryDescription;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Classify> classifies;
}
