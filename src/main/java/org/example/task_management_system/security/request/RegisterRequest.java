package org.example.task_management_system.security.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.example.task_management_system.core.dto.UserDto;


@Schema(description = "Request body for user registration")
public record RegisterRequest(

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    @Schema(description = "Username for the new user", example = "user123", required = true)
    String username,

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Schema(description = "Password for the new user", example = "strongPassword123", required = true)
    String password,

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    @Schema(description = "Email for the new user", example = "user@example.com", required = true)
    String email
) {

  public UserDto toUserDto() {
    return new UserDto()
        .setUsername(username)
        .setPassword(password)
        .setEmail(email);
  }
}

