package com.codingshuttle.springwebtutorial.springwebtutorial.controller;

import com.codingshuttle.springwebtutorial.springwebtutorial.dto.EmployeeDto;
import com.codingshuttle.springwebtutorial.springwebtutorial.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping(path = "/getSecretMessage")
    public String getSecretMessage(){
        return "Hello World";
    }

    @GetMapping(path= "/{employeeId}")
    public EmployeeDto getEmployeeById(@PathVariable Long employeeId){
        return this.employeeService.getOneEmployeeById(employeeId);

    }

    @GetMapping
    public List<EmployeeDto> getEmployeeList(@RequestParam(required = false ) String name){
        return this.employeeService.getAllEmployees();
    }

    @PostMapping
    public EmployeeDto addEmployee(@RequestBody EmployeeDto employeeDto){
        return this.employeeService.saveOneEmployee(employeeDto);
    }
}
