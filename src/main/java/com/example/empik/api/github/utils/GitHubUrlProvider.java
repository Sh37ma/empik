package com.example.empik.api.github.utils;

import org.springframework.stereotype.Component;

@Component
public class GitHubUrlProvider {
    private static final String GITHUB_URL_FORMAT = "https://api.github.com/users/%s";

    public String getUrlForUser(String user) {
        return String.format(GITHUB_URL_FORMAT, user);
    }
}
