package io.taskmanager.authentication.dao;

import io.taskmanager.authentication.domain.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
