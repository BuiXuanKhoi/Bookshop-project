package com.example.ecommerce_web.model.entities;

import com.example.ecommerce_web.model.BookState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "book_price")
    private float bookPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @Column(name = "rating")
    private float ratingPoint;

    @Column(name = "review")
    private String review;

    @Column(name = "image_link")
    private String imageLink;

    @Column(name = "create_day")
    private Date createDay;

    @Column(name = "update_day")
    private Date updateDay;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="author_id")
    private Author authors;

    @Column(name = "quantity")
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private BookState bookState;

    @OneToMany(mappedBy = "books", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

    @OneToMany(mappedBy = "books",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<Classify> classifies;

    @OneToMany(mappedBy = "books", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "books", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<OrderItems> orderItems;
}
