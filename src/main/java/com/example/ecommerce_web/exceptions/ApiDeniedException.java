package com.example.ecommerce_web.exceptions;

public class ApiDeniedException extends RuntimeException{

    public ApiDeniedException() {
        super();
    }

    public ApiDeniedException(String message) {
        super(message);
    }

    public ApiDeniedException(Throwable cause) {
        super(cause);
    }
}
