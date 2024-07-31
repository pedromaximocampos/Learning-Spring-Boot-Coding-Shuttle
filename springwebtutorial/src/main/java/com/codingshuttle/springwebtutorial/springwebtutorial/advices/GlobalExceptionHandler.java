package com.codingshuttle.springwebtutorial.springwebtutorial.advices;


import com.codingshuttle.springwebtutorial.springwebtutorial.exceptions.NoSuchResouceFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchResouceFound.class)
    public ResponseEntity<ApiResponse<?>> handleNoSuchResouceFound(NoSuchResouceFound exception) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .build();
        return createApiErrorResponse(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception exception) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .build();
        return createApiErrorResponse(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<String> exceptions = exception
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .toList();

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Input validation error")
                .subErrors(exceptions)
                .build();

        return createApiErrorResponse(apiError);

    }

    private ResponseEntity<ApiResponse<?>> createApiErrorResponse(ApiError apiError){
        return new ResponseEntity<>(new ApiResponse<>(apiError), apiError.getStatus());
    }

}
