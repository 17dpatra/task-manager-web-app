package io.taskmanager.authentication.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;


public record TaskRequest(
        String name,
        String description,
        TaskStatus status,
        String priority,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate deadline,
        Long assignee
) {}