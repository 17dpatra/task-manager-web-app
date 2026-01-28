package io.taskmanager.authentication.service;

import io.taskmanager.authentication.dao.AppUserRepository;
import io.taskmanager.authentication.dao.TeamRepository;
import io.taskmanager.authentication.dao.UserTeamMembershipRepository;
import io.taskmanager.authentication.domain.team.Team;
import io.taskmanager.authentication.domain.team.TeamRole;
import io.taskmanager.authentication.domain.user.User;
import io.taskmanager.authentication.domain.user.UserTeamMembership;
import io.taskmanager.authentication.dto.team.TeamRequest;
import io.taskmanager.authentication.dto.team.TeamResponse;
import io.taskmanager.authentication.dto.user.UserPrincipal;
import io.taskmanager.authentication.exception.AlreadyExistsException;
import io.taskmanager.authentication.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
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
        try {
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
        catch (DataIntegrityViolationException e) {
            throw new AlreadyExistsException(req.name());
        }
    }

    @Transactional
    public TeamResponse updateTeam(
            Long requesterId,
            boolean isGlobalAdmin,
            Long teamId,
            TeamRequest req
    ) {
        Team team = teamRepo.findById(teamId)
                .orElseThrow(() -> new NotFoundException("Team not found"));

        // Auth: GLOBAL_ADMIN OR team ADMIN
        if (!isGlobalAdmin) {
            UserTeamMembership myMembership = membershipRepo.findByUserIdAndTeamId(requesterId, teamId)
                    .orElseThrow(() -> new NotFoundException("Not a member of this team"));

            if (myMembership.getRole() != TeamRole.ADMIN) {
                throw new NotFoundException("Team admin privileges required");
            }
        }

        // Rename team (optional)
        if (req.name() != null && !req.name().isBlank()) {
            team.setName(req.name());
            try {
                teamRepo.save(team);
            } catch (DataIntegrityViolationException ex) {
                throw new NotFoundException(req.name());
            }
        }

        // Upsert members: add user + set role, or update role if already member
        if (req.upsertMembers() != null) {
            for (TeamRequest.MemberUpsert m : req.upsertMembers()) {
                if (m == null || m.userId() == null) continue;

                User user = userService.getReferenceById(m.userId());

                TeamRole desiredRole = (m.role() != null) ? m.role() : TeamRole.MEMBER;

                UserTeamMembership membership = membershipRepo
                        .findByUserIdAndTeamId(m.userId(), teamId)
                        .orElseGet(() -> new UserTeamMembership(user, team, desiredRole));

                membership.setRole(desiredRole);
                membershipRepo.save(membership);
            }
        }

        // Remove members
        if (req.removeUserIds() != null) {
            for (Long userIdToRemove : req.removeUserIds()) {
                if (userIdToRemove == null) continue;

                // Optional safety: don't let a non-global admin remove themselves
                if (!isGlobalAdmin && userIdToRemove.equals(requesterId)) {
                    throw new NotFoundException("You cannot remove yourself from the team");
                }

                membershipRepo.deleteByUserIdAndTeamId(userIdToRemove, teamId);
            }
        }

        return new TeamResponse(team.getId(), team.getName(), team.getCreatedBy() , team.getCreatedAt());
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
