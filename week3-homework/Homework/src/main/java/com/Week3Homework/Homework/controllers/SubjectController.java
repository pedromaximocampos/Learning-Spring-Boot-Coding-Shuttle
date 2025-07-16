package com.Week3Homework.Homework.controllers;


import com.Week3Homework.Homework.dto.SubjectDto;
import com.Week3Homework.Homework.dto.SubjectWithProfessorDto;
import com.Week3Homework.Homework.service.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }


    @GetMapping
    public ResponseEntity<List<SubjectWithProfessorDto>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectWithProfessorDto> getSubjectById(Long id) {
        return ResponseEntity.ok(subjectService.getSubjectById(id));
    }

    @PostMapping
    public ResponseEntity<SubjectDto> createSubject(@RequestBody SubjectDto subjectDto) {
        return ResponseEntity.ok(subjectService.createSubject(subjectDto));
    }


    @PutMapping("/{subjectId}/professor/{professorId}")
    public ResponseEntity<SubjectWithProfessorDto> assignProfessorToASubject(@PathVariable Long subjectId, @PathVariable Long professorId) {
        return ResponseEntity.ok(subjectService.assignProfessorToSubject(subjectId, professorId));
    }
}
