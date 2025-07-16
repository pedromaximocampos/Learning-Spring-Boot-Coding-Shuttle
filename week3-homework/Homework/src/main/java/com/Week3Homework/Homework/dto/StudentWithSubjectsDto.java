package com.Week3Homework.Homework.dto;

import com.Week3Homework.Homework.entities.AdmissionEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentWithSubjectsDto {
    
    private Long id;
    private String name;
    
    // Matérias com professor incluído
    private List<SubjectWithProfessorDto> subjects;

    private AdmissionEntity admission;
    
} 