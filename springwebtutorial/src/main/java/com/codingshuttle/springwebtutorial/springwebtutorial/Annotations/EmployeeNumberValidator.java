package com.codingshuttle.springwebtutorial.springwebtutorial.Annotations;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmployeeNumberValidator implements ConstraintValidator<EmployeeNumberValidation, Integer> {
    @Override
    public boolean isValid(Integer inputNumber, ConstraintValidatorContext constraintValidatorContext) {
        if (inputNumber < 2) {
            return false;
        }

        for (int i = 2; i < Math.sqrt(inputNumber); i++) {
            if (inputNumber % i == 0) {
                return false;
            }
        }
        return true;
    }
}
