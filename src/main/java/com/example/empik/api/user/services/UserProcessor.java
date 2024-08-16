package com.example.empik.api.user.services;

import com.example.empik.api.exceptions.InternalApiException;
import com.example.empik.api.github.models.GitHubUser;
import com.example.empik.api.user.mappers.UserMapper;
import com.example.empik.api.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserProcessor {
    private final UserMapper userMapper;

    public User convert(GitHubUser gitHubUser) {
        if (Objects.isNull(gitHubUser)) {
            throw new InternalApiException();
        }
        User user = convertCommonFields(gitHubUser);
        user.setCalculations(calculateCalculationsField(gitHubUser));
        return user;
    }

    private User convertCommonFields(GitHubUser gitHubUser) {
        try {
            return userMapper.map(gitHubUser);
        } catch (RuntimeException exception) {
            throw new InternalApiException(exception);
        }
    }

    private double calculateCalculationsField(GitHubUser gitHubUser) {
        if (gitHubUser.getFollowers() != 0) {
            return 6.0 / gitHubUser.getFollowers() * (2 + gitHubUser.getPublic_repos());
        } else {
            return 0.0;
        }
    }
}
