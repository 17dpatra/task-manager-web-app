package io.taskmanager.authentication.dto.task;

public record TaskResponse(
        Long id,
        String title,
        String description,
        String status, // TODO, IN_PROGRESS, COMPLETED
        String priority, // LOW, MEDIUM, HIGH
        String dueDate
) {}