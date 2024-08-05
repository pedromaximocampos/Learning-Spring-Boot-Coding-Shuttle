package com.codingshuttle.springwebtutorial.springwebtutorial.controller;


import com.codingshuttle.springwebtutorial.springwebtutorial.dto.DepartmentDto;
import com.codingshuttle.springwebtutorial.springwebtutorial.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping(path ="/departments")
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        List<DepartmentDto> departments = this.departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

    @GetMapping(path= "/departments/{departmentId}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable  Long departmentId) {
        DepartmentDto departmentDto = this.departmentService.getDepartmentById(departmentId);
        return ResponseEntity.ok(departmentDto);
    }

    @PostMapping(path = "/departments")
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody @Valid DepartmentDto departmentDto) {
        DepartmentDto newDepartment = this.departmentService.createNewDepartment(departmentDto);
        return ResponseEntity.ok(newDepartment);

    }

    @DeleteMapping(path = "/departments/{departmentId}")
    public ResponseEntity<DepartmentDto> deletDepartmentById(@PathVariable  Long departmentId) {
        DepartmentDto deletedDepartment = this.departmentService.deleteDepartmentById(departmentId);
        return ResponseEntity.ok(deletedDepartment);
    }

    @DeleteMapping(path = "/departments")
    public ResponseEntity<List<DepartmentDto>> deleteAllDepartments() {
        List<DepartmentDto> deleteDepartments = this.departmentService.deleteAllDepartments();
        return ResponseEntity.ok(deleteDepartments);
    }

    @PutMapping("/departments/{departmentId}")
    public ResponseEntity<DepartmentDto> updateEntireDepartment(@RequestBody @Valid DepartmentDto departmentDto, @PathVariable  Long departmentId) {
        DepartmentDto updatedDepartment = this.departmentService.updateEntireDepartment(departmentDto, departmentId);
        return ResponseEntity.ok(updatedDepartment);
    }

}
