package com.example.ecommerce_web.validator;

import com.example.ecommerce_web.exceptions.ConstraintViolateException;
import com.example.ecommerce_web.exceptions.ResourceNotFoundException;

import java.util.Collection;
import java.util.List;

public class ListValidator<T>{

    private final List<T> value;

    private ListValidator(List<T> value){
        this.value = value;
    }

    public static  <T> ListValidator<T> ofList(List<T> listEntity){
        return new ListValidator<T>(listEntity);
    }

    public List<T> ifNotEmpty() {

        if (this.value instanceof List)
        {
            if (!this.value.isEmpty())
            {
                return this.value;
            }
            throw new ResourceNotFoundException("List is Empty !!!");
        }
        throw new ConstraintViolateException("Only List Accepted !!!");
    }
}
