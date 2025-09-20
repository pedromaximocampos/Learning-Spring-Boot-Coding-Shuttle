package com.week4.prod_ready_features.prod_ready_features.dto.Auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogoutResponseDTO {

    private String message;
}
