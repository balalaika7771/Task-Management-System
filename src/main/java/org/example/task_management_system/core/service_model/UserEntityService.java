package org.example.task_management_system.core.service_model;

import base.constants.entity.EntityName;
import base.repository.JpaSpecificationExecutorRepository;
import base.service.CrudService;
import base.transformer.Transformer;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.example.task_management_system.core.dto.UserDto;
import org.example.task_management_system.core.entity.UserEntity;
import org.example.task_management_system.core.exception.EntityNotFoundException;
import org.example.task_management_system.core.repository.UserRepository;
import org.example.task_management_system.core.transformer.UserEntityTransformer;
import org.example.task_management_system.security.entity.RoleEntity;
import org.example.task_management_system.security.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class UserEntityService implements CrudService<UserDto, UserEntity, Long> {

  private final UserRepository userRepository;

  private final UserEntityTransformer userEntityTransformer;
  private final RoleRepository roleRepository;

  @Override
  public JpaSpecificationExecutorRepository<UserEntity, Long> repo() {
    return userRepository;
  }

  @Override
  public Transformer<UserDto, UserEntity> t() {
    return userEntityTransformer;
  }

  @Override
  public UserEntity enrichEntity(UserEntity entity) {
    if (entity.getId() != null && entity.getPassword() == null) {
      userRepository.findById(entity.getId())
          .ifPresent(existingUser -> entity.setPassword(existingUser.getPassword()));
    }
    return entity;
  }

  @Transactional
  public Optional<UserDto> findByUsername(String username) {
    return userRepository.findByUsername(username).map(e -> t().entityToDto(e));
  }

  @Transactional
  public Optional<UserDto> findByEmail(String email) {
    return userRepository.findByEmail(email).map(e -> t().entityToDto(e));
  }

  @Transactional
  public UserDto getByUsernameOrEmail(String username, String email) {
    try {
      return t().entityToDto(userRepository.findByUsername(username)
          .orElseThrow(() -> new EntityNotFoundException(EntityName.USER)));
    } catch (EntityNotFoundException ex) {
      return t().entityToDto(userRepository.findByEmail(email)
          .orElseThrow(() -> new EntityNotFoundException(EntityName.USER)));
    }
  }

  @Transactional
  public String getPasswordByUserId(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException(EntityName.USER))
        .getPassword();
  }

  @Override
  public UserDto saveDto(UserDto userDto) {
    var user = t().dtoToEntity(userDto);

    // Убедимся, что роли управляются Hibernate
    if (user.getRoles() != null) {
      Set<RoleEntity> managedRoles = new HashSet<>();
      for (RoleEntity role : user.getRoles()) {
        RoleEntity managedRole = roleRepository.findByName(role.getName())
            .orElseThrow(() -> new EntityNotFoundException(EntityName.ROLE));
        managedRoles.add(managedRole);
      }
      user.setRoles(managedRoles);
    }

    // Сохраняем пользователя
    return t().entityToDto(userRepository.save(user));
  }
}
