package io.taskmanager.authentication.dto.team;

import java.time.Instant;

public record TeamResponse(
        Long id,
        String name,
        Long createdBy,
        Instant createdAt
) {}
