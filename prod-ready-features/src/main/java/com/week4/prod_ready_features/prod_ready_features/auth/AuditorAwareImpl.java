package com.week4.prod_ready_features.prod_ready_features.auth;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {

        // Como não temos autenticação, vamos retornar um usuário fixo, iremos aprender auth com spring security nas proximas aulas
        return Optional.of("Pedro Máximo Campos do Carmo");
    }
}
