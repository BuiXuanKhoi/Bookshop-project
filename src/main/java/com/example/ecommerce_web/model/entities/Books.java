package com.example.ecommerce_web.model.entities;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "books")
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int book_id;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "book_price")
    private float bookPrice;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Users> users;

    @Column(name = "rating")
    private float ratingPoint;

    @Column(name = "review")
    private String review;

    @Column(name = "image_link")
    private String image_link;

    @Column(name = "create_day")
    private Date createDay;

    @Column(name = "update_day")
    private Date updateDay;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="author_id")
    private List<Author> authors;

    @Column(name = "quantity")
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private BookState bookState;
}
