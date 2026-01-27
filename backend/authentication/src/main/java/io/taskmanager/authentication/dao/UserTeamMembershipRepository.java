package io.taskmanager.authentication.dao;

import io.taskmanager.authentication.domain.team.Team;
import io.taskmanager.authentication.domain.user.UserTeamMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserTeamMembershipRepository
        extends JpaRepository<UserTeamMembership, Long> {

    @Query("""
        SELECT ut.team
        FROM UserTeamMembership ut
        WHERE ut.user.id = :userId
    """)
    List<Team> findTeamsByUserId(Long userId);
}
