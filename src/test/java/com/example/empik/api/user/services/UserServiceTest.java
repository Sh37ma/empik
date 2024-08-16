package com.example.empik.api.user.services;

import com.example.empik.api.database.Visit;
import com.example.empik.api.database.VisitRepository;
import com.example.empik.api.exceptions.InternalApiException;
import com.example.empik.api.github.models.GitHubUser;
import com.example.empik.api.github.services.GitHubService;
import com.example.empik.api.user.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private GitHubService gitHubService;
    @Mock
    private VisitRepository visitRepository;
    @Mock
    private UserProcessor userProcessor;
    @InjectMocks
    private UserService userService;

    @Test
    void shouldCorrectlyFetchGitHubUserData() {
        // given
        String login = "login";
        GitHubUser gitHubUser = new GitHubUser();
        ArgumentCaptor<Visit> visitCaptor = ArgumentCaptor.forClass(Visit.class);

        when(gitHubService.fetchGitHubUserData(login)).thenReturn(gitHubUser);
        when(visitRepository.findByLogin(login)).thenReturn(Optional.of(getVisit()));
        when(userProcessor.convert(gitHubUser)).thenReturn(new User());

        // when
        User result = userService.getUserData(login);

        // then
        verify(gitHubService).fetchGitHubUserData(login);
        verify(visitRepository).findByLogin(login);

        verify(visitRepository).save(visitCaptor.capture());
        assertThat(visitCaptor.getValue().getRequestCount()).isEqualTo(2);

        verify(userProcessor).convert(gitHubUser);
        assertThat(result).isEqualTo(new User());
    }

    @Test
    void shouldThrowInternalApiExceptionWhenProblemWithDbOccurred() {
        // given
        String login = "login";
        GitHubUser gitHubUser = new GitHubUser();

        when(gitHubService.fetchGitHubUserData(login)).thenReturn(gitHubUser);
        when(visitRepository.findByLogin(login)).thenThrow(DataRetrievalFailureException.class);

        // when & then
        assertThatExceptionOfType(InternalApiException.class)
                .isThrownBy(() -> userService.getUserData(login))
                .withMessage("Internal error occurred, please restart application");
    }

    private static Visit getVisit() {
        Visit visit = new Visit();
        visit.setRequestCount(1);
        return visit;
    }
}