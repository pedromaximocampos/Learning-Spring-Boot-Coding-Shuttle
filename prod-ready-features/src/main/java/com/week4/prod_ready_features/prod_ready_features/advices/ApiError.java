package com.week4.prod_ready_features.prod_ready_features.advices;


import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {

    private HttpStatus statusCode;
    private String error;
    private LocalDateTime timestamp;

    public ApiError(HttpStatus statusCode, String error) {
        this();
        this.statusCode = statusCode;
        this.error = error;

    }

    public ApiError() {
        this.timestamp = LocalDateTime.now();
    }
}
