package io.taskmanager.authentication.dto.task;

public record TaskRequest(
        Long id,
        String title,
        String description,
        String status // TODO, IN_PROGRESS, COMPLETED
) {}