package com.Week3Homework.Homework.controllers;

import com.Week3Homework.Homework.dto.ProfessorDto;
import com.Week3Homework.Homework.dto.ProfessorWithSubjectsDto;
import com.Week3Homework.Homework.service.ProfessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/professors")
public class ProfessorController {

    private final ProfessorService professorService;


    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }


    @GetMapping
    public ResponseEntity<List<ProfessorDto>> getAllProfessors() {
        return ResponseEntity.ok(professorService.getAllProfessors());
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProfessorWithSubjectsDto> getProfessorById(Long id) {
        return ResponseEntity.ok(professorService.getProfessorById(id));
    }


    @PostMapping
    public ResponseEntity<ProfessorDto> createProfessor(ProfessorDto professorDto) {
        return ResponseEntity.ok(professorService.createProfessor(professorDto));
    }
}
