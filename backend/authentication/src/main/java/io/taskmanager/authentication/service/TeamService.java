package io.taskmanager.authentication.service;

import io.taskmanager.authentication.dao.AppUserRepository;
import io.taskmanager.authentication.dao.TeamRepository;
import io.taskmanager.authentication.dao.UserTeamMembershipRepository;
import io.taskmanager.authentication.domain.team.Team;
import io.taskmanager.authentication.domain.team.TeamRole;
import io.taskmanager.authentication.domain.user.UserTeamMembership;
import io.taskmanager.authentication.dto.team.TeamRequest;
import io.taskmanager.authentication.dto.team.TeamResponse;
import io.taskmanager.authentication.dto.user.UserPrincipal;
import io.taskmanager.authentication.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepo;
    private final UserTeamMembershipRepository membershipRepo;
    private final AppUserRepository userService;

    public TeamService(TeamRepository teamRepo,
                       UserTeamMembershipRepository membershipRepo, AppUserRepository userService) {
        this.teamRepo = teamRepo;
        this.membershipRepo = membershipRepo;
        this.userService = userService;
    }

    public List<TeamResponse> getMyTeams(UserPrincipal user) {
        return membershipRepo.findTeamsByUserId(user.id())
                .stream()
                .map(TeamService::toResponse)
                .toList();
    }

    @Transactional
    public TeamResponse createTeam(UserPrincipal creator, TeamRequest req) {
        Team team = new Team();
        team.setName(req.name().trim());
        team.setCreatedBy(creator.id());

        Team saved = teamRepo.save(team);

        // creator becomes OWNER
        UserTeamMembership membership = new UserTeamMembership(
                userService.getReferenceById(creator.id()),
                saved,
                TeamRole.ADMIN
        );
        membershipRepo.save(membership);

        return toResponse(saved);
    }

    @Transactional
    public TeamResponse updateTeam(Long teamId, TeamRequest req) {
        Team team = teamRepo.findById(teamId)
                .orElseThrow(() -> new NotFoundException(teamId));

        team.setName(req.name().trim());
        return toResponse(teamRepo.save(team));
    }

    @Transactional
    public void deleteTeam(Long teamId) {
        Team team = teamRepo.findById(teamId)
                .orElseThrow(() -> new NotFoundException(teamId));

        teamRepo.delete(team);
    }

    private static TeamResponse toResponse(Team t) {
        return new TeamResponse(
                t.getId(),
                t.getName(),
                t.getCreatedBy(),
                t.getCreatedAt()
        );
    }
}
