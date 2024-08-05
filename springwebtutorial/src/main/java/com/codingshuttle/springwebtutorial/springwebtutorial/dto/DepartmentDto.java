package com.codingshuttle.springwebtutorial.springwebtutorial.dto;


import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {

    private Long id;

    @NotBlank(message = "Title cannot be Blank")
    @Size(min = 2, max = 50)
    private String title;

    @AssertTrue(message = "Department need to be active")
    private Boolean isActive;

    @NotBlank(message = "Date of creation cannot be Blank")
    @PastOrPresent(message = "Date of creation cannot be in the future")
    private LocalDateTime createdAt;
}
