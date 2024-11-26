package org.example.task_management_system.security.dto;

import base.abstractions.IdentifiableDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * DTO (Data Transfer Object) для представления роли пользователя.
 *
 * @author Ivan Zhendorenko
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Schema(description = "DTO для представления роли пользователя")
public class RoleDto extends IdentifiableDto<RoleDto> {

  /**
   * Название роли.
   */
  @Schema(description = "Название роли", example = "admin")
  private String name;

  /**
   * Разрешения, связанные с ролью.
   */
  @Schema(description = "Набор разрешений, связанных с ролью")
  private Set<PermissionDto> permissions;
}
