package com.example.ecommerce_web.security.service;

import com.example.ecommerce_web.constant.Role;
import com.example.ecommerce_web.constant.UserState;
import com.example.ecommerce_web.model.entities.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class UserDetail implements UserDetails {



    private final int userId;
    private final String userName;
    private final String userPassword;
    private final Collection<? extends GrantedAuthority> authorities;
    private final UserState userState;

    public UserDetail(int userId, String userName,
                      String userPassword, Collection<? extends GrantedAuthority> authorities, UserState userState) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.authorities = authorities;
        this.userState = userState;
    }

    public static UserDetail init(Users users){
        List<GrantedAuthority> authorities = new ArrayList<>();
        Role userRole = users.getRole();
        String role = userRole.name();

        authorities.add(new SimpleGrantedAuthority(role));

        return new UserDetail(
                users.getUserId(),
                users.getUserName(),
                users.getPassword(),
                authorities,
                users.getUserState()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return userPassword;
    }


    @Override
    public String getUsername() {
        return userName;
    }

    public int getUserId(){
        return userId;
    }

    public UserState getUserState() {
        return userState;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userState.equals(UserState.UNBLOCK);
    }
}
