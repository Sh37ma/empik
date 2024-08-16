package com.example.empik.api.github.utils;

import com.example.empik.api.github.models.GitHubUser;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GitHubUrlProviderTest {
    private static final String GITHUB_URL_FORMAT = "https://api.github.com/users/";
    private final GitHubUrlProvider gitHubUrlProvider = new GitHubUrlProvider();
    @Test
    void getUrlForUser() {
        // given
        String user = "user";
        // when
        String result = gitHubUrlProvider.getUrlForUser(user);
        // then
        assertThat(result).isEqualTo(GITHUB_URL_FORMAT + user);
    }
}