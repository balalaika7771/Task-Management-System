package org.example.task_management_system.security.dto;

import base.abstractions.IdentifiableDto;
import base.constants.entity.AccessLevel;
import base.constants.entity.EntityName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * DTO (Data Transfer Object) для представления разрешений.
 * <p>
 * Разрешение включает информацию о сущности, на которую распространяется доступ,
 * и уровень доступа.
 *
 * @author Ivan Zhendorenko
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Schema(description = "DTO для представления разрешений")
public class PermissionDto extends IdentifiableDto<PermissionDto> {

  /**
   * Название сущности, на которую распространяется разрешение.
   */
  @Schema(description = "Название сущности, на которую распространяется разрешение", example = "TASK")
  private EntityName entityName;

  /**
   * Уровень доступа, связанный с разрешением.
   */
  @Schema(description = "Уровень доступа, связанный с разрешением", example = "OWN_R")
  private AccessLevel accessLevel;
}
