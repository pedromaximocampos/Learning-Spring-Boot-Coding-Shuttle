package com.codingShuttle.week3.Jpa.Tutorial.controllers;


import com.codingShuttle.week3.Jpa.Tutorial.dto.DepartmentDto;
import com.codingShuttle.week3.Jpa.Tutorial.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/departments")
public class DepartmentController {

    private final DepartmentService departmentService;


    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable Long id) {

        return  ResponseEntity.ok(departmentService.getDepartmentById(id));
    }


    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto departmentDto) {

        return ResponseEntity.ok(departmentService.createDepartment(departmentDto));
    }


    @PutMapping("/{departmentId}/assign-manager/{employeeId}")
    public ResponseEntity<DepartmentDto> assignManagerToDepartement(@PathVariable Long departmentId, @PathVariable Long employeeId) {

        return ResponseEntity.ok(departmentService.assignManagerToDepartement(departmentId, employeeId));
    }


    @PutMapping("/{departmentId}/assign-employee/{employeeId}")
    public ResponseEntity<DepartmentDto> assingWorkerToDepartment(@PathVariable Long departmentId, @PathVariable Long employeeId) {

        return ResponseEntity.ok(departmentService.assingWorkerToDepartment(departmentId, employeeId));
    }

}
