package io.taskmanager.authentication.dto.team;

import io.taskmanager.authentication.domain.team.TeamRole;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record TeamRequest(
        @NotBlank String name,
        Set<MemberUpsert> upsertMembers,   // add or update role
        Set<Long> removeUserIds            // remove from team
) {
    public record MemberUpsert(
            Long userId,
            TeamRole role                  // ADMIN or MEMBER (if null -> default MEMBER)
    ) {}
}
