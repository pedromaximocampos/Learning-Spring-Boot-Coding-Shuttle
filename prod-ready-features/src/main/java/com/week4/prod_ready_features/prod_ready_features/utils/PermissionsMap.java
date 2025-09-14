package com.week4.prod_ready_features.prod_ready_features.utils;

import com.week4.prod_ready_features.prod_ready_features.entities.enums.Permission;


import com.week4.prod_ready_features.prod_ready_features.entities.enums.Role;


import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.week4.prod_ready_features.prod_ready_features.entities.enums.Permission.*;
import com.week4.prod_ready_features.prod_ready_features.entities.enums.Role.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


public class PermissionsMap {


    private static final Map<Role, Set<Permission>> map =  Map.of(
            Role.USER, Set.of(Permission.USER_VIEW, Permission.POST_VIEW),
            Role.CREATOR, Set.of(Permission.POST_CREATE, Permission.POST_UPDATE, Permission.USER_UPDATE),
            Role.ADMIN, Set.of(Permission.POST_DELETE, Permission.USER_DELETE, Permission.USER_CREATE)

    );


    public static final Set<SimpleGrantedAuthority> getAuthoritiesForRole(Role role){
        return map.get(role)
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }
}
