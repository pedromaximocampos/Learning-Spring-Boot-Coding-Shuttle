package com.Week3Homework.Homework.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorWithSubjectsDto {
    
    private Long id;
    private String name;
    
    // Lista de matérias usando DTO existente
    private List<SubjectDto> subjects;
    
    // Informações adicionais úteis
    private Integer totalSubjects;
} 