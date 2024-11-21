package org.example.task_management_system.security.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.example.task_management_system.core.dto.UserDto;


@Schema(description = "Request body for user authentication")
public record AuthRequest(
    @NotBlank(message = "Username or email cannot be empty")
    @Schema(description = "Username or email of the user", example = "example@gmail.com or username123")
    String usernameOrEmail,

    @NotBlank(message = "Password cannot be empty")
    @Schema(description = "Password of the user", example = "password123")
    String password
) {

  public UserDto toUserDto() {
    UserDto userDto = new UserDto().setPassword(password);

    // Проверяем, является ли usernameOrEmail email'ом
    if (usernameOrEmail.contains("@")) {
      userDto.setEmail(usernameOrEmail);
    } else {
      userDto.setUsername(usernameOrEmail);
    }

    return userDto;
  }
}
