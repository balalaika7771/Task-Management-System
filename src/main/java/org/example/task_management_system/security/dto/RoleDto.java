package org.example.task_management_system.security.dto;

import base.abstractions.IdentifiableDto;
import java.util.Set;
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
public class RoleDto extends IdentifiableDto<RoleDto> {

  private String name;

  private Set<PermissionDto> permissions;
}
