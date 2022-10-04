package com.example.ecommerce_web.exceptions.handler;


import com.example.ecommerce_web.exceptions.ConstraintViolateException;
import com.example.ecommerce_web.exceptions.MethodNotImplementedException;
import com.example.ecommerce_web.exceptions.RefreshTokenExpiredException;
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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    protected ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex){

        ErrorRespond errorRespond = ErrorRespond.builder()
                                                .message(ex.getMessage())
                                                .statusCode(HttpStatus.NOT_FOUND.value())
                                                .build();

        return new ResponseEntity<>(errorRespond, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler({ConstraintViolateException.class})
    protected ResponseEntity<?> handleConstraintViolateException(ConstraintViolateException ex){

        ErrorRespond errorRespond = ErrorRespond.builder()
                                                .message(ex.getMessage())
                                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                                .build();

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

        ErrorRespond errorRespond = ErrorRespond.builder()
                                                .message("Validate Error !!!")
                                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                                .validateMessage(errors)
                                                .build();

        return new ResponseEntity<Object>(errorRespond, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AccessDeniedException.class})
    protected  ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex){

        ErrorRespond errorRespond = ErrorRespond.builder()
                                                .message("You don't have enough permission to access this api")
                                                .statusCode(HttpStatus.METHOD_NOT_ALLOWED.value())
                                                .build();
        return new ResponseEntity<>(errorRespond, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler({DisabledException.class})
    protected ResponseEntity<?> handleDisableException(DisabledException disabledException){

        ErrorRespond errorRespond = ErrorRespond.builder()
                                                .message("Account has been disable !!!")
                                                .statusCode(HttpStatus.UNAUTHORIZED.value())
                                                .build();

        return new ResponseEntity<>(errorRespond, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({MethodNotImplementedException.class})
    protected ResponseEntity<?> handleMethodNotImplementedException(MethodNotImplementedException ex){

        ErrorRespond errorRespond = ErrorRespond.builder()
                                                .message(ex.getMessage())
                                                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                .build();

        return new ResponseEntity<>(errorRespond, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    protected ResponseEntity<?> handleMethodTypeMisMatchException(MethodArgumentTypeMismatchException ex){
        ErrorRespond errorRespond = ErrorRespond.builder()
                                                .message("One of required parameters or path is missed. Please check it out !!!")
                                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                                .build();

        return new ResponseEntity<>(errorRespond, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RefreshTokenExpiredException.class})
    protected ResponseEntity<?> handleRefreshTokenExpiredException(RefreshTokenExpiredException ex){
        ErrorRespond errorRespond = ErrorRespond.builder()
                                                .message("Your refresh token is expired. Please make another login")
                                                .statusCode(HttpStatus.UNAUTHORIZED.value())
                                                .build();

        return new ResponseEntity<>(errorRespond, HttpStatus.UNAUTHORIZED);
    }
}
