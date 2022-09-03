package com.example.ecommerce_web.model.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="Author")

public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private int authorID;

    @Column(name = "author_name")
    private String authorName;
}
