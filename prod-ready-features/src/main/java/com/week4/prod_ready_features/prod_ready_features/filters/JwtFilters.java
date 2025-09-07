package com.week4.prod_ready_features.prod_ready_features.filters;


import com.week4.prod_ready_features.prod_ready_features.entities.UserEntity;
import com.week4.prod_ready_features.prod_ready_features.services.Auth.JwtService;
import com.week4.prod_ready_features.prod_ready_features.services.Users.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.SqlReturnType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@NoArgsConstructor
public class JwtFilters extends OncePerRequestFilter {

    private JwtService jwtService;
    private UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.split("Bearer ")[1];
        Long userId = jwtService.getUserIdFromToken(jwt);

        if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserEntity userEntity = userService.getUserById(userId);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userEntity, null, null
            );
            // pega dados da requisição do usuario Ip, sessao, localização
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // seta o usuario como autenticado no contexto do spring
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        // segue com o fluxo de filtro de seguranca do spring security
        filterChain.doFilter(request, response);

        // se necessario e possivel realizar novas instrucoes apos o filtro ser executado:
    }
}
