package com.codingShuttle.week3.Jpa.Tutorial.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public org.modelmapper.ModelMapper getModelMapper() {
        return new org.modelmapper.ModelMapper();
    }
}
