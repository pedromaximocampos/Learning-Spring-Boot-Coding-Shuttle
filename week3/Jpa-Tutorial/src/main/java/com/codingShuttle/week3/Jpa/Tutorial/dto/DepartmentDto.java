package com.codingShuttle.week3.Jpa.Tutorial.dto;


import com.codingShuttle.week3.Jpa.Tutorial.entities.EmployeeEntity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {

    private Long id;

    @NotBlank
    private String title;

    private EmployeeDto manager;

    private Set<EmployeeDto> employees;

}
