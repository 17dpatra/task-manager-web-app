package io.taskmanager.authentication.dto.task;

import java.time.LocalDate;

public record TaskResponse(
        Long id,
        String title,
        String description,
        String status, // TODO, IN_PROGRESS, COMPLETED
        String priority, // LOW, MEDIUM, HIGH
        LocalDate dueDate
) {}