package io.taskmanager.authentication.dto.user;

import io.taskmanager.authentication.domain.user.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UserRequest(
        @NotBlank @Size(min = 3, max = 50) String username,
        @NotBlank @Size(min = 8, max = 100) String password,
        @Size(max = 100) String displayName,
        Set<UserRole> roles) {

}
