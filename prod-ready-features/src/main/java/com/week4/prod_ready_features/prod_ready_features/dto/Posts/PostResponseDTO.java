package com.week4.prod_ready_features.prod_ready_features.dto.Posts;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO {

        private Long id;

        @NotNull
        private String title;

        @NotNull
        private String description;

}
