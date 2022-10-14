package com.example.ecommerce_web.security;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.security.service.UserLocal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
public class UserLocalTest {

    UserLocal userLocal = new UserLocal();


    @Test
    void whenGetLocalUserName_thenThrowResourceNotFoundException_ifUserHasNotSigned(){
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(null);
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> userLocal.getLocalUserName());

        assertThat(exception.getMessage(), is("You haven't Login !!!"));
    }

    @Test
    void whenGetLocalUserName_thenReturnUserName_ifUserSigned(){
        String userName = "Khoi";
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(userName);
        assertThat(userLocal.getLocalUserName(), is(userName));
    }
}
