package io.taskmanager.authentication.dto.team;

import io.taskmanager.authentication.domain.team.TeamRole;

public record TeamMembershipResponse(long userId, TeamRole role) {
}
