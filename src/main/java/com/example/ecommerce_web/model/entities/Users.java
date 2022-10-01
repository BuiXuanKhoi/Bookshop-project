package com.example.ecommerce_web.model.entities;

import com.example.ecommerce_web.constant.Role;
import com.example.ecommerce_web.constant.UserState;
import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "user_name")
    @NotEmpty(message = "user name is required")
    @NotNull(message = "user name must not be null")
    private String userName;

    @Column(name = "password")
    @NotEmpty(message = "password is required")
    @NotNull(message = "password must not be null")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "roles")
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "states")
    private UserState userState;

    @Column(name = "lock_time")
    private Date lockTime;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Orders> orders;

    @OneToOne(mappedBy="users",fetch = FetchType.LAZY)
    private Information information;

    @OneToMany(mappedBy = "users",fetch = FetchType.LAZY)
    private List<Books> books;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

    public Users(String userName, String password, Role role, UserState userState) {
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.userState = userState;
    }
}
