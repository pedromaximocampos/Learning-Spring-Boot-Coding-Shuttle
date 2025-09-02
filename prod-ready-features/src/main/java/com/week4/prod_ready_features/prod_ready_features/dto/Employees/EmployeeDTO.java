package com.week4.prod_ready_features.prod_ready_features.dto.Employees;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private Long id;

    @NotBlank(message = "Name field cannot be Blank")
    @Size(min = 2, max = 15, message = "Name field must be at least 2 characters and max of 15 characters.")
    private String name;

    @NotBlank(message = "Email field cannot be Blank")
    @Email(message = "Email field must be a valid email")
    private String email;


    private LocalDate dateOfJoining;

    @AssertTrue(message = "Employee must be active")
    private Boolean isActive;


    @NotBlank
    @Size(min = 10, max = 20,  message = "Password field must be at least 10 characters and max of 20 characters.")
    private String password;

    @NotNull
    private Integer number;


}