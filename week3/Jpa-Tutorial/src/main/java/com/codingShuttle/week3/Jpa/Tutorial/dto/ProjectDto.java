package com.codingShuttle.week3.Jpa.Tutorial.dto;


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
public class ProjectDto {


    private Long id;

    @NotNull(message = "Title cannot be null")
    private String title;

    private DepartmentDto departmentResponsible;

    private Set<EmployeeDto> members;
}
