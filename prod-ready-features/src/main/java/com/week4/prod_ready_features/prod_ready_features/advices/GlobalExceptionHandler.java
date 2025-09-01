package com.week4.prod_ready_features.prod_ready_features.advices;

import com.week4.prod_ready_features.prod_ready_features.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiError handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ApiError(org.springframework.http.HttpStatus.NOT_FOUND, ex.getMessage());
    }
}
