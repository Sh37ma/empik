package com.example.empik.api.github.services;

import com.example.empik.api.exceptions.GitHubServiceException;
import com.example.empik.api.exceptions.GitHubUserNotFoundException;
import com.example.empik.api.github.models.GitHubUser;
import com.example.empik.api.github.utils.GitHubUrlProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GitHubService {
    private final GitHubUrlProvider gitHubUrlProvider;
    private final RestTemplate restTemplate;

    public GitHubUser fetchGitHubUserData(String userName) {
        try {
            String readyUrl = gitHubUrlProvider.getUrlForUser(userName);
            return restTemplate.getForObject(readyUrl, GitHubUser.class);

        } catch (HttpClientErrorException.NotFound exception) {
            throw new GitHubUserNotFoundException(userName);
        } catch (RestClientException exception) {
            throw new GitHubServiceException();
        }
    }

}
