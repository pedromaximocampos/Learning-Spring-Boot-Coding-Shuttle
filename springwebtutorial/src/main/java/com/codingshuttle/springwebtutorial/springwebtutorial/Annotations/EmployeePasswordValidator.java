package com.codingshuttle.springwebtutorial.springwebtutorial.Annotations;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmployeePasswordValidator implements ConstraintValidator<EmployeePasswordValidation, String> {

    @Override
    public boolean isValid(String inputPassword, ConstraintValidatorContext constraintValidatorContext) {
        if (inputPassword.length() < 10) {
            return false;
        }

        return this.containsLowerCase(inputPassword) && this.containsUpperCase(inputPassword) && this.containsSpecialCharacter(inputPassword);
    }

    private Boolean containsUpperCase(String inputPassword) {
        for (char c : inputPassword.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    private Boolean containsLowerCase(String inputPassword) {
        for (char c : inputPassword.toCharArray()) {
            if (Character.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }

    private Boolean containsSpecialCharacter(String inputPassword) {
        String specialCharacters = "!@#$%^&*()_+[]{}|;:'\\\",.<>?/~`-=";
        for (char c : inputPassword.toCharArray()) {
            if (specialCharacters.indexOf(c) >= 0) {
                return true;
            }
        }
        return false;
    }
}





