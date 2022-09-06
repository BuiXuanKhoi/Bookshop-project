package com.example.ecommerce_web.model.entities;


import com.example.ecommerce_web.model.Location;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
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
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "address")
    private Location address;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "user_id")
    private Users users;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    public Information( String lastName, String firstName, Date dayOfBirth, String email, Location address, String phoneNumber, Date createDate, Date updateDate){
        this.lastName = lastName;
        this.firstName = firstName;
        this.dateOfBirth = dayOfBirth;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
