package io.taskmanager.authentication.dao;

import io.taskmanager.authentication.domain.team.Team;
import io.taskmanager.authentication.domain.user.UserTeamId;
import io.taskmanager.authentication.domain.user.UserTeamMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserTeamMembershipRepository
        extends JpaRepository<UserTeamMembership, UserTeamId> {

    @Query("""
        SELECT ut.team
        FROM UserTeamMembership ut
        WHERE ut.user.id = :userId
    """)
    List<Team> findTeamsByUserId(Long userId);

    List<UserTeamMembership> findByTeamId(Long teamId);

    Optional<UserTeamMembership> findByUserIdAndTeamId(Long userId, Long teamId);


    void deleteByUserIdAndTeamId(Long userId, Long teamId);
}
