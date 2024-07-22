package com.codingshuttle.springwebtutorial.springwebtutorial.controller;

import com.codingshuttle.springwebtutorial.springwebtutorial.dto.EmployeeDto;
import com.codingshuttle.springwebtutorial.springwebtutorial.entity.EmployeeEntity;
import com.codingshuttle.springwebtutorial.springwebtutorial.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }



    @GetMapping(path = "/getSecretMessage")
    public String getSecretMessage(){
        return "Hello World";
    }

    @GetMapping(path= "/{employeeId}")
    public EmployeeEntity getEmployeeById(@PathVariable Long employeeId){
        return employeeRepository.findById(employeeId).orElse(null);

    }

    @GetMapping
    public List<EmployeeEntity> getEmployeeList(@RequestParam(required = false ) String name){
        return employeeRepository.findAll();
    }

    @PostMapping
    public EmployeeEntity addEmployee(@RequestBody EmployeeEntity employeeEntity){
        return employeeRepository.save(employeeEntity);
    }
}
