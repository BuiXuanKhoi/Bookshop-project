package com.example.ecommerce_web.model.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "classify")
public class Classify {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "classify_id")
    private int classifyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Books books;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
