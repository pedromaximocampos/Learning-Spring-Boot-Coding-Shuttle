package com.codingshuttle.springwebtutorial.springwebtutorial.controller;

import com.codingshuttle.springwebtutorial.springwebtutorial.dto.EmployeeDto;
import com.codingshuttle.springwebtutorial.springwebtutorial.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable @Valid Long employeeId){
        EmployeeDto employeeFound = this.employeeService.getOneEmployeeById(employeeId);
        return ResponseEntity.ok(employeeFound);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getEmployeeList(@RequestParam(required = false ) String name){
        List<EmployeeDto> employees =  this.employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> addEmployee(@RequestBody @Valid EmployeeDto employeeDto){
        EmployeeDto newEmployeeDto  = this.employeeService.saveOneEmployee(employeeDto);
        return ResponseEntity.ok(newEmployeeDto);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeDto> putEmployeeById(@RequestBody @Valid EmployeeDto employeeDto, @PathVariable Long employeeId){
        EmployeeDto newEmployeeDto = this.employeeService.putEmployeeById(employeeId, employeeDto);
        return ResponseEntity.ok(newEmployeeDto);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<EmployeeDto> deleteEmployeeById(@PathVariable Long employeeId){
        EmployeeDto employeeDto = this.employeeService.deleteEmployeeById(employeeId);
        return ResponseEntity.ok(employeeDto);
    }

    @PatchMapping("/{employeeId}")
    public ResponseEntity<EmployeeDto> patchEmployeeById(@RequestBody Map<String, Object> updates, @PathVariable Long employeeId){
        EmployeeDto newEmployeeDto = this.employeeService.updateEmployeePropertiesById(employeeId, updates);
        if (newEmployeeDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(newEmployeeDto);
    }
}

