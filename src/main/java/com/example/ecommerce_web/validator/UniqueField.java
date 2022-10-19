package com.example.ecommerce_web.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueFieldValidator.class)
public @interface UniqueField {

    String message() default "Field not unique";

    Validator entity();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
