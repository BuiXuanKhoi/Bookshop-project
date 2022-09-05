package com.example.ecommerce_web.exceptions;

public class UnAuthorizationException extends RuntimeException{

    public UnAuthorizationException() {
        super();
    }

    public UnAuthorizationException(String message) {
        super(message);
    }

    public UnAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
