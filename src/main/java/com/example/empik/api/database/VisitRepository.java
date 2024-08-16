package com.example.empik.api.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitRepository extends CrudRepository<Visit, Long> {
    Optional<Visit> findByLogin(String login);
}
