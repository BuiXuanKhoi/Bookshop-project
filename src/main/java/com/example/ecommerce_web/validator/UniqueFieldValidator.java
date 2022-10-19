package com.example.ecommerce_web.validator;

import com.example.ecommerce_web.exceptions.ConstraintViolateException;
import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.entities.Author;
import com.example.ecommerce_web.repository.AuthorRepository;
import com.example.ecommerce_web.repository.BookRepository;
import com.example.ecommerce_web.repository.CategoryRepository;
import com.example.ecommerce_web.repository.UserRepository;
import com.fasterxml.jackson.databind.util.BeanUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.Serializable;

@NoArgsConstructor
public class UniqueFieldValidator implements ConstraintValidator<UniqueField, Object>{


    private String message;
    private Validator entity;


    private UserRepository userRepository;

    private AuthorRepository authorRepository;

    private CategoryRepository categoryRepository;

    private BookRepository bookRepository;


    @Override
    public void initialize(UniqueField constraintAnnotation) {
        this.userRepository = (UserRepository) BeanProvider.getBean(UserRepository.class);
        this.authorRepository = (AuthorRepository) BeanProvider.getBean(AuthorRepository.class);
        this.categoryRepository = (CategoryRepository) BeanProvider.getBean(CategoryRepository.class);
        this.bookRepository = (BookRepository) BeanProvider.getBean(BookRepository.class);
        message = constraintAnnotation.message();
        entity = constraintAnnotation.entity();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        boolean isExist = switch (entity){
            case CATEGORY -> this.categoryRepository.existsByCategoryName((String) value);
            case BOOK -> this.bookRepository.existsByBookName((String) value);
            case AUTHOR -> this.authorRepository.existsByAuthorName((String) value);
            case USER -> this.userRepository.existsByUserName((String) value);
            case BOOK_REQUEST -> this.bookRepository.getByAuthorId((Integer) value) != null;
            default -> throw new ResourceNotFoundException("Not Found Field Validator !!!");
        };


        if (isExist){
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
