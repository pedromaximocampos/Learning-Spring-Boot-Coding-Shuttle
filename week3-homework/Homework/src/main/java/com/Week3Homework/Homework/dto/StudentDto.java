package com.Week3Homework.Homework.dto;

import com.Week3Homework.Homework.entities.AdmissionEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    
    private Long id;
    private String name;

    private AdmissionWithoutStudentDto admission;

} 