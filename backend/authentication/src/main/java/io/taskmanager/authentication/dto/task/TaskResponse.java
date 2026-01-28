package io.taskmanager.authentication.dto.task;

public record TaskResponse(
        String title,
        String description,
        String status // TODO, IN_PROGRESS, COMPLETED
) {}