package com.example.empik.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class GitHubServiceException extends RuntimeException {

    public static final String SERVICE_UNAVAILABLE_MESSAGE = "GitHub service is not available right now, please again later";

    public GitHubServiceException() {
        super(SERVICE_UNAVAILABLE_MESSAGE);
    }
}
