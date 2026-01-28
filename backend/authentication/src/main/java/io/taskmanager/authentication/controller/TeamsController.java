package io.taskmanager.authentication.controller;


import io.taskmanager.authentication.domain.user.User;
import io.taskmanager.authentication.dto.team.TeamRequest;
import io.taskmanager.authentication.dto.team.TeamResponse;
import io.taskmanager.authentication.dto.user.UserPrincipal;
import io.taskmanager.authentication.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teams")
public class TeamsController {

    private final TeamService teams;

    public TeamsController(TeamService teams) {
        this.teams = teams;
    }

    // -------------------------
    // Get teams I belong to
    // -------------------------
    @GetMapping
    public List<TeamResponse> getMyTeams(
            @AuthenticationPrincipal UserPrincipal me
    ) {
        return teams.getMyTeams(me);
    }

    // -------------------------
    // Create team (ADMIN only)
    // -------------------------
    @PostMapping
    @PreAuthorize("hasAuthority('GLOBAL_ADMIN')")
    public TeamResponse createTeam(
            @AuthenticationPrincipal UserPrincipal me,
            @Valid @RequestBody TeamRequest req
    ) {
        return teams.createTeam(me, req);
    }

    // -------------------------
    // Update team (ADMIN only)
    // -------------------------
    @PutMapping("/{teamId}")
    @PreAuthorize("hasAuthority('GLOBAL_ADMIN')")
    public TeamResponse updateTeam(
            @PathVariable Long teamId,
            @Valid @RequestBody TeamRequest req
    ) {
        return null;
        //return teams.updateTeam(teamId, req);
    }

    // -------------------------
    // Delete team (ADMIN only)
    // -------------------------
    @DeleteMapping("/{teamId}")
    @PreAuthorize("hasAuthority('GLOBAL_ADMIN')")
    public void deleteTeam(@PathVariable Long teamId) {
        teams.deleteTeam(teamId);
    }
}
