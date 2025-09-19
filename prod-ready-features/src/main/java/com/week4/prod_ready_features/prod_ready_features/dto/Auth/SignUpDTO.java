package com.week4.prod_ready_features.prod_ready_features.dto.Auth;


import com.week4.prod_ready_features.prod_ready_features.entities.enums.Role;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@Data
public class SignUpDTO {

    private String email;
    private String password;
    private String name;
    private Set<Role> roles;
}
