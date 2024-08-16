package com.example.empik.api.user.services;

import com.example.empik.api.database.Visit;
import com.example.empik.api.database.VisitRepository;
import com.example.empik.api.user.models.User;
import com.example.empik.api.exceptions.InternalApiException;
import com.example.empik.api.github.models.GitHubUser;
import com.example.empik.api.github.services.GitHubService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final GitHubService gitHubService;
    private final VisitRepository visitRepository;
    private final UserProcessor userProcessor;

    public User getUserData(String login) {
        GitHubUser gitHubUser = gitHubService.fetchGitHubUserData(login);
        saveIncreasedLoginCount(login);
        return userProcessor.convert(gitHubUser);
    }

    private void saveIncreasedLoginCount(String login) {
        try {
            Visit visit = visitRepository.findByLogin(login)
                    .orElse(new Visit(login, 0));
            incrementRequestCount(visit);
            visitRepository.save(visit);
        } catch (DataAccessException exception) {
            throw new InternalApiException(exception);
        }
    }

    private void incrementRequestCount(Visit visit) {
        visit.setRequestCount(visit.getRequestCount() + 1);
    }

}
