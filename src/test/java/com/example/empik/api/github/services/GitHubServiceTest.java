package com.example.empik.api.github.services;

import com.example.empik.api.exceptions.GitHubServiceException;
import com.example.empik.api.exceptions.GitHubUserNotFoundException;
import com.example.empik.api.github.models.GitHubUser;
import com.example.empik.api.github.utils.GitHubUrlProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GitHubServiceTest {
    public static final String URL = "url";
    public static final String USER = "user";
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private GitHubUrlProvider gitHubUrlProvider;
    @InjectMocks
    private GitHubService gitHubService;

    @BeforeEach
    void setUp(){
        when(gitHubUrlProvider.getUrlForUser(USER)).thenReturn(URL);
    }

    @Test
    void shouldCorrectlyFetchGitHubUserData() {
        // given
        GitHubUser gitHubUser = new GitHubUser();
        when(restTemplate.getForObject(URL, GitHubUser.class)).thenReturn(gitHubUser);
        // when
        GitHubUser result = gitHubService.fetchGitHubUserData(USER);
        // then
        verify(gitHubUrlProvider).getUrlForUser(USER);
        verify(restTemplate).getForObject(URL, GitHubUser.class);
        assertThat(result).isEqualTo(gitHubUser);
    }

    @Test
    void shouldThrowGitHubUserNotFoundException() {
        // given
        when(restTemplate.getForObject(URL, GitHubUser.class))
                .thenThrow(HttpClientErrorException.NotFound.class);
        // when & then
        assertThatExceptionOfType(GitHubUserNotFoundException.class)
                .isThrownBy(() -> gitHubService.fetchGitHubUserData(USER))
                .withMessage("GitHub user with login: [" + USER + "] not found. Please check user login");
    }
    @Test
    void shouldThrowGitHubServiceException() {
        // given
        when(restTemplate.getForObject(URL, GitHubUser.class))
                .thenThrow(RestClientException.class);
        // when & then
        assertThatExceptionOfType(GitHubServiceException.class)
                .isThrownBy(() -> gitHubService.fetchGitHubUserData(USER))
                .withMessage("GitHub service is not available right now, please again later");
    }

}