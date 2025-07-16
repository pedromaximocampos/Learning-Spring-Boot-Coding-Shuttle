package com.Week3Homework.Homework.controllers;


import com.Week3Homework.Homework.dto.StudentDto;
import com.Week3Homework.Homework.dto.StudentWithSubjectsDto;
import com.Week3Homework.Homework.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }


    @GetMapping("/{id}")
    public ResponseEntity<StudentWithSubjectsDto> getStudentById(Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PostMapping
    public ResponseEntity<StudentDto> createStudent(StudentDto studentDto) {
        return ResponseEntity.ok(studentService.createStudent(studentDto));
    }

    @PutMapping("/{id}/assign-subjects")
    public ResponseEntity<StudentWithSubjectsDto> assignSubjectsToStudent(@PathVariable Long id, @RequestBody List<Long> subjectIds) {
        return ResponseEntity.ok(studentService.assignSubjectsToStudent(id, subjectIds));
    }



}
