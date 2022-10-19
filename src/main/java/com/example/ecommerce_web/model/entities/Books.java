package com.example.ecommerce_web.model.entities;

import com.example.ecommerce_web.constant.BookState;
import com.example.ecommerce_web.validator.UniqueField;
import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@EqualsAndHashCode
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
    @NotNull
    @NotBlank(message = "Book name cannot be empty")
    private String bookName;

    @Column(name = "book_price")
    private float bookPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @CreatedBy
    private Users users;

    @Column(name = "rating")
    private float ratingPoint;

    @Column(name = "review")
    private String review;

    @Column(name = "image_link")
    private String imageLink;

    @Column(name = "create_day")
    @CreationTimestamp
    private Date createDay;

    @Column(name = "update_day")
    @UpdateTimestamp
    private Date updateDay;

    @Column(name = "description", length = 10000)
    private String description;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="author_id", unique = true)
    private Author authors;

    @Column(name = "quantity")
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")

    private BookState bookState;

    @OneToMany(mappedBy = "books", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

    @OneToMany(mappedBy = "books",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Classify> classifies;

//    @ManyToMany
//    @JoinTable(
//            name = "classify",
//            joinColumns  = @JoinColumn(name = "book_id"),
//            inverseJoinColumns  = @JoinColumn(name = "category_id")
//    )
//    private Set<Category> categories;

    @OneToMany(mappedBy = "books", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "books", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<OrderItems> orderItems;

}
