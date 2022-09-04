package com.example.ecommerce_web.exceptions.handler;


import com.example.ecommerce_web.exceptions.ConstraintViolateException;
import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.dto.respond.ErrorRespond;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex){

        String message = ex.getMessage();
        int statusCode = HttpStatus.NOT_FOUND.value();

        return new ResponseEntity<>(new ErrorRespond(statusCode, message), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ConstraintViolateException.class})
    public ResponseEntity<?> handleConstraintViolateException(ConstraintViolateException ex){

        String message = ex.getMessage();
        int statusCode = HttpStatus.BAD_REQUEST.value();
        return new ResponseEntity<>(new ErrorRespond(statusCode, message), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }
}
