package org.example.task_management_system.core.dto;

import base.abstractions.IdentifiableDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.example.task_management_system.security.dto.RoleDto;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class UserDto extends IdentifiableDto<UserDto> {

  @NotNull
  private String username;

  //Будет null если замапен из ентити бд
  private String password;

  @NotNull
  @Email
  private String email;

  private Set<RoleDto> roles;
}
