package com.codingShuttle.week3.Jpa.Tutorial.controllers;

import com.codingShuttle.week3.Jpa.Tutorial.dto.EmployeeDto;
import com.codingShuttle.week3.Jpa.Tutorial.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    public EmployeeDto getEmployeeById(@PathVariable Long id) {

        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto) {

        return employeeService.createEmployee(employeeDto);
    }
}
