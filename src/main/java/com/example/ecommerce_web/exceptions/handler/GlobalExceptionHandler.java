package com.example.ecommerce_web.exceptions.handler;


import com.example.ecommerce_web.exceptions.ConstraintViolateException;
import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.dto.respond.ErrorRespond;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    protected ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex){

        String message = ex.getMessage();
        int statusCode = HttpStatus.NOT_FOUND.value();

        ErrorRespond errorRespond = new ErrorRespond(statusCode, message);

        return new ResponseEntity<>(errorRespond, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler({ConstraintViolateException.class})
    protected ResponseEntity<?> handleConstraintViolateException(ConstraintViolateException ex){

        String message = ex.getMessage();
        int statusCode = HttpStatus.BAD_REQUEST.value();

        ErrorRespond errorRespond = new ErrorRespond(statusCode, message);
        return new ResponseEntity<>( errorRespond, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            errors.put(fieldName, errorMessage);
        });

        int statusCode = HttpStatus.BAD_REQUEST.value();
        String message = "Validation Error";
        ErrorRespond error = new ErrorRespond(statusCode,message , errors);
        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AccessDeniedException.class})
    protected  ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex){
        int statusCode = HttpStatus.FORBIDDEN.value();
        String message = "You don't have enough permission to access this api";

        ErrorRespond errorRespond = new ErrorRespond(statusCode, message);
        return new ResponseEntity<>(errorRespond, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler({DisabledException.class})
    protected ResponseEntity<?> handleDisableException(DisabledException disabledException){
        int statusCode = HttpStatus.UNAUTHORIZED.value();
        String message = "Account has been disable !!!";

        ErrorRespond errorRespond = new ErrorRespond(statusCode, message);

        return new ResponseEntity<>(errorRespond, HttpStatus.UNAUTHORIZED);
    }
}
