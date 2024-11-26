package org.example.task_management_system.core.dto;

import base.abstractions.IdentifiableDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.example.task_management_system.security.dto.RoleDto;


/**
 * DTO (Data Transfer Object) для представления пользователя.
 *
 * @author Ivan Zhendorenko
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Schema(description = "DTO для представления пользователя")
public class UserDto extends IdentifiableDto<UserDto> {

  /**
   * Имя пользователя.
   */
  @NotNull
  @Schema(description = "Имя пользователя", example = "john_doe")
  private String username;

  /**
   * Пароль пользователя.
   * Может быть null, если объект был получен из сущности базы данных.
   */
  @Schema(description = "Пароль пользователя", example = "secure_password")
  private String password;

  /**
   * Email пользователя.
   */
  @NotNull
  @Email
  @Schema(description = "Email пользователя", example = "john.doe@example.com")
  private String email;

  /**
   * Роли, назначенные пользователю.
   */
  @Schema(description = "Роли, назначенные пользователю")
  private Set<RoleDto> roles;
}
