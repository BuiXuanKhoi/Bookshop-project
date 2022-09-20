package com.example.ecommerce_web.model.dto.respond;

import com.example.ecommerce_web.model.entities.Users;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InformationRespondDTO {

    private Date dateOfBirth;
    private String address;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private Date createDate;
    private Date updateDate;


}
