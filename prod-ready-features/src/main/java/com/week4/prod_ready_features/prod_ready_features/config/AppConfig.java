package com.week4.prod_ready_features.prod_ready_features.config;


import com.week4.prod_ready_features.prod_ready_features.auth.AuditorAwareImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "getAuditorAwareProvider")
public class AppConfig {

    @Bean
    ModelMapper getModelMapper(){
        return new ModelMapper();
    }

    @Bean
    public AuditorAwareImpl getAuditorAwareProvider() {
        return new AuditorAwareImpl();
    }
}
