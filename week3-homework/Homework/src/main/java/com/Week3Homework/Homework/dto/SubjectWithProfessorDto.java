package com.Week3Homework.Homework.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectWithProfessorDto {
    
    private Long id;
    private String title;
    
    // Professor usando DTO simples (evita referência circular)
    private ProfessorDto professor;
} 