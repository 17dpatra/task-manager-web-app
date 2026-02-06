package io.taskmanager.authentication.dto.task;

import java.time.LocalDate;

public record TaskResponse(

        Long id,
        String name,
        String description,
        TaskStatus status,
        String priority,
        LocalDate deadline,
        Long assignedUserId
) {}