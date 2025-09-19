package com.week4.prod_ready_features.prod_ready_features.dto.Auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDTO {

    private String email;
    private String password;
}
