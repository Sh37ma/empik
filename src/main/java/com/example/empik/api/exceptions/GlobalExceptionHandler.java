package com.example.empik.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String ERROR = "error";
    public static final String STATUS = "status";

    @ExceptionHandler(GitHubUserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(GitHubUserNotFoundException exception) {
        Map<String, String> error = createResponseBody(exception.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GitHubServiceException.class)
    public ResponseEntity<Map<String, String>> handleGitHubServiceException(GitHubServiceException exception) {
        Map<String, String> error = createResponseBody(exception.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(InternalApiException.class)
    public ResponseEntity<Map<String, String>> handleInternalApiException(InternalApiException exception) {
        Map<String, String> error = createResponseBody(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static Map<String, String> createResponseBody(String message, HttpStatus httpStatus) {
        Map<String, String> error = new HashMap<>();
        error.put(ERROR, message);
        error.put(STATUS, httpStatus.name());
        return error;
    }

}
