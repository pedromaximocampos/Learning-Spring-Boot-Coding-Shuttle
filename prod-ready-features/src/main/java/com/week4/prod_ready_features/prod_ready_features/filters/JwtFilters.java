package com.week4.prod_ready_features.prod_ready_features.filters;


import com.week4.prod_ready_features.prod_ready_features.entities.UserEntity;
import com.week4.prod_ready_features.prod_ready_features.services.Auth.JwtService;
import com.week4.prod_ready_features.prod_ready_features.services.Auth.UserSessionService;
import com.week4.prod_ready_features.prod_ready_features.services.Users.UserService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.SqlReturnType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@Order(1)
@RequiredArgsConstructor
public class JwtFilters extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;


    // Temos que injetar o HandlerExceptionResolver para tratar exceções dentro do filtro, pois o contexto do filtro
    // não é o mesmo do contexto do controller, logo o @ControllerAdvice não funciona aqui.
    // Usamos o Autowired com Qualifier para especificar o bean correto do HandlerExceptionResolver, pois com o NoArgsConstructor
    // o Spring não consegue injetar automaticamente via construtor, por ja ter um Bean criado para o HandlerExceptionResolver.
    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver handlerExceptionResolver;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {

            final String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String jwt = authHeader.split("Bearer ")[1];
            Long userId = jwtService.getUserIdFromToken(jwt);

            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
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
        }catch (Exception exception){
            // Aqui estamos delegando o tratamento da exceção para o HandlerExceptionResolver,
            // que por sua vez irá acionar o @ControllerAdvice configurado na aplicação.
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}
