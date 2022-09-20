package com.example.ecommerce_web.model.dto.respond;


import com.example.ecommerce_web.constant.Location;
import com.example.ecommerce_web.constant.Role;
import com.example.ecommerce_web.constant.UserState;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
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

    private Role roleName;

    private UserState userState;
}
