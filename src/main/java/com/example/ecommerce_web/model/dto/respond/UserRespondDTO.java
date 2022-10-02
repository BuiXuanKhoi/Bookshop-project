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

    private String email;

    private Location location;

    private Date createDate;

    private Date updateDate;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private Role role;

    private UserState userState;
}
