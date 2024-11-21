package org.example.task_management_system.core.dto;

import base.abstractions.IdentifiableDto;
import base.constants.entity.AccessLevel;
import base.constants.entity.EntityName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class PermissionDto extends IdentifiableDto<PermissionDto> {

  private EntityName entityName;

  private AccessLevel accessLevel;
}
