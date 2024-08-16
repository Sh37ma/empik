package com.example.empik.api.user.services;

import com.example.empik.api.exceptions.InternalApiException;
import com.example.empik.api.github.models.GitHubUser;
import com.example.empik.api.user.mappers.UserMapper;
import com.example.empik.api.user.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserProcessorTest {

    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserProcessor userProcessor;

    @Test
    void shouldCorrectlyFetchGitHubUserData() {
        // given
        GitHubUser gitHubUser = createGitHubUser(1);
        when(userMapper.map(gitHubUser)).thenReturn(new User());
        // when
        User result = userProcessor.convert(gitHubUser);
        // then
        verify(userMapper).map(gitHubUser);
        assertThat(result).isEqualTo(createExpectedUser(12.0));
    }

    @Test
    void shouldCorrectlyFetchGitHubUserDataWhenFollowersAreZero() {
        // given
        GitHubUser gitHubUser = createGitHubUser(0);
        when(userMapper.map(gitHubUser)).thenReturn(new User());
        // when
        User result = userProcessor.convert(gitHubUser);
        // then
        verify(userMapper).map(gitHubUser);
        assertThat(result).isEqualTo(createExpectedUser(0.0));
    }

    @Test
    void shouldThrowInternalApiExceptionWhenGitHubUserIsNull() {
        // given & when & then
        assertThatExceptionOfType(InternalApiException.class)
                .isThrownBy(() -> userProcessor.convert(null))
                .withMessage("Internal error occurred, please restart application");
    }

    private static User createExpectedUser(double calculations) {
        User user = new User();
        user.setCalculations(calculations);
        return user;
    }

    private static GitHubUser createGitHubUser(int followers) {
        GitHubUser gitHubUser = new GitHubUser();
        gitHubUser.setFollowers(followers);
        gitHubUser.setPublic_repos(0);
        return gitHubUser;
    }

}
