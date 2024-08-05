package com.codingshuttle.springwebtutorial.springwebtutorial.Annotations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = {EmployeeNumberValidator.class})
public @interface EmployeeNumberValidation {
    String message() default "Invalid Number. Employee Number must be a prime number.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
