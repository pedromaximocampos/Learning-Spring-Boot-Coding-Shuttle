package com.week4.prod_ready_features.prod_ready_features.dto.Auth;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

    private Long userId;
    private String accessToken;
    private String refreshToken;

}
