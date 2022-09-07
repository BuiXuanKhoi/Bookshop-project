package com.example.ecommerce_web.config;


import com.example.ecommerce_web.security.jwt.JwtAuthEntryPoint;
import com.example.ecommerce_web.security.jwt.JwtAuthTokenFilter;
import com.example.ecommerce_web.security.jwt.JwtUtils;
import com.example.ecommerce_web.security.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.swing.text.html.HTML;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SuppressWarnings("Deprecated ")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailServiceImpl userDetailServiceImpl;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final JwtUtils jwtUtils;

    @Autowired
    public WebSecurityConfig(UserDetailServiceImpl userDetailService, JwtAuthEntryPoint jwtAuthEntryPoint, JwtUtils jwtUtils) {
        this.userDetailServiceImpl = userDetailService;
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
        this.jwtUtils = jwtUtils;
    }

    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter(){
        return new JwtAuthTokenFilter(jwtUtils, userDetailServiceImpl);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailServiceImpl).passwordEncoder(encode());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public PasswordEncoder encode(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/books").permitAll()
                .antMatchers(HttpMethod.POST,"/api/books").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/users/information").hasAnyAuthority("CUSTOMER", "ADMIN")
                .antMatchers(HttpMethod.PUT,"api/users/password").hasAnyAuthority("CUSTOMER","ADMIN")
                .antMatchers(HttpMethod.PUT,"api/books").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE,"api/books").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST,"api/books/category").hasAuthority("ADMIN")
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
