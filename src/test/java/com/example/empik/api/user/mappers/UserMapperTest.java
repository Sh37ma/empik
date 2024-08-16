package com.example.empik.api.user.mappers;

import com.example.empik.api.github.models.GitHubUser;
import com.example.empik.api.user.models.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserMapperTest {
    public static final int ID = 1;
    public static final String LOGIN = "login";
    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final String AVATAR_URL = "avatar_url";
    public static final String CREATED_AT = "created_at";
    public static final int FOLLOWERS = 10;
    public static final int PUBLIC_REPOS = 5;
    public static final double CALCULATIONS = 0;
    private final UserMapper userMapper = new UserMapperImpl();

    @Test
    void shouldCorrectlyMapUser() {
        // given
        GitHubUser gitHubUser = createGitHubUser();
        User expectedUser = createUser();
        // when
        User result = userMapper.map(gitHubUser);
        // then
        assertThat(result).isEqualTo(expectedUser);
    }

    private static GitHubUser createGitHubUser() {
        GitHubUser gitHubUser = new GitHubUser();
        gitHubUser.setId(ID);
        gitHubUser.setLogin(LOGIN);
        gitHubUser.setName(NAME);
        gitHubUser.setType(TYPE);
        gitHubUser.setAvatar_url(AVATAR_URL);
        gitHubUser.setCreated_at(CREATED_AT);
        gitHubUser.setFollowers(FOLLOWERS);
        gitHubUser.setPublic_repos(PUBLIC_REPOS);
        return gitHubUser;
    }
    private static User createUser() {
        User user = new User();
        user.setId(ID);
        user.setLogin(LOGIN);
        user.setName(NAME);
        user.setType(TYPE);
        user.setAvatarUrl(AVATAR_URL);
        user.setCreatedAt(CREATED_AT);
        user.setCalculations(CALCULATIONS);
        return user;
    }
}