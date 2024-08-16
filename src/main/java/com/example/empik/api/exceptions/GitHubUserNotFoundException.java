package com.example.empik.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GitHubUserNotFoundException extends RuntimeException {
    public static final String GITHUB_USER_NOT_FOUND_MESSAGE = "GitHub user with login: [%s] not found. Please check user login";

    public GitHubUserNotFoundException(String userName) {
        super(String.format(GITHUB_USER_NOT_FOUND_MESSAGE, userName));
    }
}
