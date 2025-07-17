package com.Week3Homework.Homework.dto;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdmissionWithoutStudentDto {

    private Long id;

    @NonNull
    private Integer fees;
}
