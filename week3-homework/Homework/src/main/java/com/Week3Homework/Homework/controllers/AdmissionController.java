package com.Week3Homework.Homework.controllers;


import com.Week3Homework.Homework.dto.AdmissionDto;
import com.Week3Homework.Homework.service.AdmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admissions")
public class AdmissionController {


    private final AdmissionService admissionService;

    public AdmissionController(AdmissionService admissionService) {
        this.admissionService = admissionService;
    }


    @GetMapping
    public ResponseEntity<List<AdmissionDto>> getAdmissionInfo() {
        return ResponseEntity.ok(admissionService.getAllAdmissions());

    }


    @GetMapping("/{id}")
    public ResponseEntity<AdmissionDto> getAdmissionById(@PathVariable Long id) {
        return ResponseEntity.ok(admissionService.getAdmissionById(id));
    }

    @PostMapping("/create-admission/{studentId}")
    public ResponseEntity<AdmissionDto> createAdmission(@RequestBody AdmissionDto admissionDto, @PathVariable Long studentId) {
        return ResponseEntity.ok(admissionService.createAdmission(admissionDto, studentId));
    }
}
