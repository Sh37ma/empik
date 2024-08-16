package com.example.empik.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalApiException extends RuntimeException {

    public static final String INTERNAL_ERROR_MESSAGE = "Internal error occurred, please restart application";

    public InternalApiException(Throwable throwable) {
        super(INTERNAL_ERROR_MESSAGE, throwable);
    }
    public InternalApiException() {
        super(INTERNAL_ERROR_MESSAGE);
    }
}
