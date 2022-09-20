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



    private int userId;
    private String userName;
    private String userPassword;
    private Collection<? extends GrantedAuthority> authorities;
    private UserState userState;
    private  Date lockTime;

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
        users.getUserState();

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

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
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
