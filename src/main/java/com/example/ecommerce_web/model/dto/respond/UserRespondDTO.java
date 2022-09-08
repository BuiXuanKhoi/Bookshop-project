package com.example.ecommerce_web.model.dto.respond;


import com.example.ecommerce_web.model.Location;
import com.example.ecommerce_web.model.UserState;
import com.example.ecommerce_web.model.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRespondDTO {

    private int userId;

    private String userName;

    private String userEmail;

    private Location userLocation;

    private Date userCreateDay;

    private Date userEditDay;

    private String userFirstName;

    private String userLastName;

    private String userPhoneNumber;

    private String roleName;
}
