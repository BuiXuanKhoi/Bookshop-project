package com.example.ecommerce_web.model.dto.request;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyUserRequestDTO {

    private Date dateOfBirth;

    private String firstName;

    private String lastName;

    private String address;

    private String phoneNumber;

    private String email;

    private String userName;

    private Date createDate;

    private Date updateDate;
}
