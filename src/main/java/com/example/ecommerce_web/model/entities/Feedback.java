package com.example.ecommerce_web.model.entities;

import lombok.*;
import org.apache.catalina.User;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.awt.print.Book;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feedbacks")

public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private int feedbackId;

    @Column(name =  "comment")
    @NotEmpty(message = "comment is required")
    @NotNull(message = "comment must not be null")
    private String comment;

    @Column(name = "create_day")
    @CreationTimestamp
    private Date createDay;

    @Column(name = "feedback_title")
    @NotEmpty(message = "title is required")
    @NotNull(message = "title must not be null")
    private String title;

    @Column(name =  "rating_points")
    @NotEmpty(message = "rating point is required")
    @NotNull(message = "rating point must not be null")
    private float ratingPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotEmpty(message = "users is required")
    @NotNull(message = "users must not be null")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    @NotEmpty(message = "book is required")
    @NotNull(message = "book must not be null")
    private Books books;
}
