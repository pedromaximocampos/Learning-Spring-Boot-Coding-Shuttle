package com.Week3Homework.Homework.dto;

import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdmissionDto {

    private Long id;

    @NonNull
    private Integer fees;

    private StudentDto studentesAdmitted;


}
