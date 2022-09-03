package com.example.ecommerce_web.model.entities;

import com.example.ecommerce_web.model.UserState;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Data
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "states")
    private UserState userState;

    @Column(name = "lock_time")
    private Date lockTime;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Orders> orders;

    @OneToMany(mappedBy = "users")
    private List<Books> books;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

}
