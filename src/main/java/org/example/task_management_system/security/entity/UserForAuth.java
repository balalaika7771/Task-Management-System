package org.example.task_management_system.security.entity;


import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.task_management_system.core.dto.RoleDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@AllArgsConstructor
@Getter
@Setter
public class UserForAuth implements UserDetails {

  private String username;

  private String password;

  private Set<RoleDto> roles;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles.stream()
        .filter(role -> role.getPermissions() != null) // Исключаем null-коллекции permissions
        .flatMap(role -> role.getPermissions().stream())
        .filter(Objects::nonNull) // Исключаем null-объекты permission
        .map(permission -> new SimpleGrantedAuthority(
            permission.getEntityName() + "_" + permission.getAccessLevel()))
        .collect(Collectors.toSet());
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
