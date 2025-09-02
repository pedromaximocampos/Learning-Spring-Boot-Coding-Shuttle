package com.week4.prod_ready_features.prod_ready_features.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
public class RestClientConfig {

    @Value("${employee.service.url}")
    private String BASE_URL_EMPLOYEE;


    @Bean
    @Qualifier("employeeRestClient")
    RestClient getEmployeeRestClient(){
        return RestClient.builder()
                .baseUrl(BASE_URL_EMPLOYEE)
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .defaultStatusHandler(HttpStatusCode::is5xxServerError,(req, res) ->{
                            throw new RuntimeException("Employee Service is down. Please try again later.");
                        })
                .build();
    }

}
