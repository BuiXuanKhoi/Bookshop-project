package com.example.ecommerce_web.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import javax.persistence.*;
import java.awt.print.Book;
import java.util.Date;
import java.util.List;

@Data
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
    private String comment;

    @Column(name = "create_day")
    private Date createDay;

    @Column(name =  "rating_points")
    private float ratingPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Books books;
}
