package com.codingshuttle.springwebtutorial.springwebtutorial.controller;

import com.codingshuttle.springwebtutorial.springwebtutorial.dto.EmployeeDto;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @GetMapping(path = "/getSecretMessage")
    public String getSecretMessage(){
        return "Hello World";
    }

    @GetMapping
    public EmployeeDto getEmployeeById(@RequestParam Long id){
        return new EmployeeDto(id, "pedro", "pedro@gmail.com", LocalDate.of(2024, 7 , 22), true);

    }

    @PostMapping
    public EmployeeDto addEmployee(@RequestBody EmployeeDto employeeDto){
        employeeDto.setId(100L);
        return employeeDto;
    }
}
