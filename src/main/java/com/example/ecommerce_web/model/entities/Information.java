package com.example.ecommerce_web.model.entities;


import com.example.ecommerce_web.constant.Location;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "information")
public class Information {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "information_id")

    private int informationId;

    @Column(name = "date_of_birth")
    @NotEmpty(message = "date of birth is required")
    @NotNull(message = "date of birth must not be null")
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "address")
    @NotEmpty(message = "address is required")
    @NotNull(message = "address must not be null")
    private Location address;

    @Column(name = "email")
    @NotEmpty(message = "email is required")
    @NotNull(message = "email must not be null")
    private String email;

    @Column(name = "phone_number")
    @NotEmpty(message = "phone number is required")
    @NotNull(message = "phone number must not be null")
    private String phoneNumber;

    @OneToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "user_id")
    @NotEmpty(message = "users is required")
    @NotNull(message = "users must not be null")
    private Users users;

    @Column(name = "first_name")
    @NotEmpty(message = "first name is required")
    @NotNull(message = "first name must not be null")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "last name is required")
    @NotNull(message = "last name must not be null")
    private String lastName;

    @Column(name = "create_date")
    @CreationTimestamp
    private Date createDate;

    @Column(name = "update_date")
    @UpdateTimestamp
    private Date updateDate;
}
