package com.example.ecommerce_web.controller;

import com.example.ecommerce_web.model.dto.request.RefreshTokenRequest;
import com.example.ecommerce_web.model.dto.request.UserRequestDTO;
import com.example.ecommerce_web.model.dto.request.LoginRequestDTO;
import com.example.ecommerce_web.model.dto.respond.LoginRespondDTO;
import com.example.ecommerce_web.model.dto.respond.MessageRespond;
import com.example.ecommerce_web.model.dto.respond.RefreshTokenResponse;
import com.example.ecommerce_web.service.AuthService;
import com.example.ecommerce_web.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(maxAge = 3600, origins = "*")
public class AuthController {

    AuthService authService;
    RefreshTokenService refreshTokenService;

    @Autowired
    public AuthController(AuthService authService, RefreshTokenService refreshTokenService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
    }

    @Operation(
            summary = "Sign Up",
            description = "When send Http Request to this API with request body contains enough and valid field as recommend," +
                    "Server will create new user base on request body. Please notice that you can sign up in any role with this API," +
                    "both Admin and Users. " +
                    "If the API return status code 404 and message that the Role not available, this means the role you want to signup not available." +
                    "If there is any field invalid, Server will return 400 bad request status code and the message. "
    )
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid UserRequestDTO userRequestDTO){
        return this.authService.signup(userRequestDTO);
    }


    @Operation(
            summary = "Login",
            description = "Send Http Request to this API with request body contains enough and valid fields as recommend." +
                    " Server will create new JWT and refresh token and return to you. " +
                    " The JWT Token used to authorize user and the refresh token used to create new JWT without re-login. " +
                    " If any information that you enter is not exist, the Server will return the 404 status code."
    )
    @PostMapping("/login")
    public LoginRespondDTO login(@RequestBody @Valid LoginRequestDTO loginRequestDTO){
        return this.authService.login(loginRequestDTO);
    }


    @Operation(
            summary = "Request new JWT based on refresh token",
            description = "Send Http Request to this API with request body contains enough and valid field as recommend. " +
                    "Server will create new JWT token base on the refresh token in request body. " +
                    "If the refresh token not exist, Server will return 404 status code." +
                    "If the refresh token is expired, Server will return 401 status code. "
    )
    @PostMapping("/token")
    public RefreshTokenResponse refreshToken(@RequestBody @Valid RefreshTokenRequest request){
        return this.refreshTokenService.createToken(request);
    }

    @Operation(
            summary = "Log out",
            description = " Send Http Request to this API to delete the Refresh token."
    )
    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(){
        return this.authService.logout();
    }
}
