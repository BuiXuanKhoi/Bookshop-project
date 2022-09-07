package com.example.ecommerce_web.exceptions.handler;


import com.example.ecommerce_web.exceptions.ConstraintViolateException;
import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.dto.respond.ErrorRespond;
import com.fasterxml.jackson.databind.deser.impl.ErrorThrowingDeserializer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex){

        String message = ex.getMessage();
        int statusCode = HttpStatus.NOT_FOUND.value();

        ErrorRespond errorRespond = new ErrorRespond(statusCode, message);

        return new ResponseEntity<>(errorRespond, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException ex){
        String message = "Enum constant not exists ";

        int statusCode =  HttpStatus.BAD_REQUEST.value();

        ErrorRespond errorRespond = new ErrorRespond(statusCode,message);

        return new ResponseEntity<>(errorRespond, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({ConstraintViolateException.class})
    public ResponseEntity<?> handleConstraintViolateException(ConstraintViolateException ex){

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
        ErrorRespond error = new ErrorRespond(400, "Validation Error", errors);
        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AccessDeniedException.class})
    protected  ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex){
        int statusCode = HttpStatus.FORBIDDEN.value();
        String message = "You don't have enough permission to access this api";

        ErrorRespond errorRespond = new ErrorRespond(statusCode, message);
        return new ResponseEntity<>(errorRespond, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    protected ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex){
        int statusCode = HttpStatus.BAD_REQUEST.value();
        String message = "VALUE NOT AVAILABLE";

        ErrorRespond errorRespond = new ErrorRespond(statusCode, message);
        return new ResponseEntity<>(errorRespond, HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<?> handleApiDeniedException(ApiDeniedException ex){
        int statusCode = HttpStatus.FORBIDDEN.value();
        String message = ex.getMessage();

        ErrorRespond errorRespond = new ErrorRespond(statusCode, message);

        return new ResponseEntity<>(errorRespond, HttpStatus.FORBIDDEN);
    }


}
