package com.codingshuttle.springwebtutorial.springwebtutorial.Annotations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = {EmployeePasswordValidator.class})
public @interface EmployeePasswordValidation {
    String message() default "Invalid Password. Employee password must have 1 Uppercase letter, 1 Lowercase letter, 1 special character";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
