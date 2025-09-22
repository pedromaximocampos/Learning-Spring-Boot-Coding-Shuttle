package com.week4.prod_ready_features.prod_ready_features;


import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class TestContainerConfiguration {

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> mySQLContainer(){
        return new PostgreSQLContainer<>(DockerImageName.parse("postgres:17.4")); // no caso iriamos colocar
                                                                                        //  a versao do banco em producao
    }
}
