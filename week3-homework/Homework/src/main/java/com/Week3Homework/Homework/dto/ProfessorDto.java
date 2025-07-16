package com.Week3Homework.Homework.dto;


import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorDto {

    private Long id;

    @NonNull
    private String name;

}
