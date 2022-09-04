package com.example.ecommerce_web.exceptions;

public class ConstraintViolateException extends RuntimeException{

    public ConstraintViolateException() {
        super();
    }

    public ConstraintViolateException(String message) {
        super(message);
    }

    public ConstraintViolateException(String message, Throwable cause) {
        super(message, cause);
    }
}
