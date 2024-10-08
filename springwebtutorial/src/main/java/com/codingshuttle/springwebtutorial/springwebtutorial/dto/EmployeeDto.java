package com.codingshuttle.springwebtutorial.springwebtutorial.dto;

import com.codingshuttle.springwebtutorial.springwebtutorial.Annotations.EmployeeNumberValidation;
import com.codingshuttle.springwebtutorial.springwebtutorial.Annotations.EmployeePasswordValidation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private Long id;

    @NotBlank(message = "Name field cannot be Blank")
    @Size(min = 2, max = 15, message = "Name field must be at least 2 characters and max of 15 characters.")
    private String name;

    @NotBlank(message = "Email field cannot be Blank")
    @Email(message = "Email field must be a valid email")
    private String email;

    @NotBlank(message = "Date of Joining cannot be Blank")
    @PastOrPresent(message = "Date of Joining cannot be in the future")
    private LocalDate dateOfJoining;

    @AssertTrue(message = "Employee must be active")
    private Boolean isActive;


    @NotBlank
    @Size(min = 10, max = 20,  message = "Password field must be at least 10 characters and max of 20 characters.")
    @EmployeePasswordValidation
    private String password;

    @NotNull
    @EmployeeNumberValidation
    private Integer number;
}