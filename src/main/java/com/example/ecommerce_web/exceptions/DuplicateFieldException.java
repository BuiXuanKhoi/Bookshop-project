package com.example.ecommerce_web.exceptions;

public class DuplicateFieldException extends RuntimeException{

    public DuplicateFieldException() {
    }

    public DuplicateFieldException(String message) {
        super(message);
    }
}
