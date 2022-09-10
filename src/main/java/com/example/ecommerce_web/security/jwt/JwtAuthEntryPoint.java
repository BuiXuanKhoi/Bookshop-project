package com.example.ecommerce_web.security.jwt;


import com.example.ecommerce_web.model.dto.respond.ErrorRespond;
import com.example.ecommerce_web.model.dto.respond.JwtErrorRespond;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        int statusCode = HttpStatus.UNAUTHORIZED.value();
        String message;
        String detail;

        if(request.getAttribute("invalid signature") != null)
        {
            message = "Invalid Signature";
            detail = String.valueOf(request.getAttribute("invalid signature"));
        }
        else if(request.getAttribute("invalid jwt") != null)
        {
            message = "Invalid JWT";
            detail = String.valueOf(request.getAttribute("invalid jwt"));
        }
        else if(request.getAttribute("expired") != null)
        {
            message = "Expired JWT Token";
            detail = String.valueOf(request.getAttribute("expired"));
        }
        else if (request.getAttribute("unsupported") != null)
        {
            message = "Unsupported JWT Token";
            detail = String.valueOf(request.getAttribute("unsupported"));
        }
        else if (request.getAttribute("empty") != null)
        {
            message = "Token is empty";
            detail = String.valueOf(request.getAttribute("empty"));
        }
        else
        {
            message = "Cannot determine error";
            detail = null;
        }

        JwtErrorRespond jwtError = new JwtErrorRespond(statusCode, message, detail);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mapper.writeValueAsString(jwtError));
    }
}
