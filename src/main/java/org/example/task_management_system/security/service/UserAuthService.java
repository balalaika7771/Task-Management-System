package org.example.task_management_system.security.service;

import base.constants.entity.AccessLevel;
import base.constants.entity.EntityName;
import base.constants.entity.RoleNames;
import base.service.abstractions.BaseService;
import base.transformer.Transformer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.example.task_management_system.core.dto.UserDto;
import org.example.task_management_system.core.exception.EmailExistsException;
import org.example.task_management_system.core.exception.InvalidCredentialsException;
import org.example.task_management_system.core.exception.NameExistsException;
import org.example.task_management_system.core.service_model.RoleEntityService;
import org.example.task_management_system.core.service_model.UserEntityService;
import org.example.task_management_system.security.entity.UserForAuth;
import org.example.task_management_system.security.transformer.UserForAuthTransformer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class UserAuthService implements UserDetailsService, BaseService<UserDto, UserForAuth> {

  private final UserEntityService userEntityService;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final RoleEntityService roleEntityService;
  private final UserForAuthTransformer userForAuthTransformer;

  @Transactional
  public String register(UserDto newUser) {
    var user = userEntityService.findByUsername(newUser.getUsername());
    if (user.isPresent()) {
      throw new NameExistsException(newUser.getUsername());
    }

    user = userEntityService.findByEmail(newUser.getEmail());
    if (user.isPresent()) {
      throw new EmailExistsException(newUser.getEmail());
    }

    // Кодируем пароль, выдаем роль и сохраняем пользователя
    newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
    newUser.setRoles(
        new HashSet<>(Collections.singleton(
            roleEntityService.findByName(RoleNames.USER).orElseThrow(() -> new RuntimeException("Нет базовой роли")))));
    var savedUser = userEntityService.saveDto(newUser);

    // Генерируем JWT-токен для нового пользователя
    return jwtService.generateToken(savedUser);
  }


  @Transactional
  public String login(UserDto entryUser) throws InvalidCredentialsException {
    var user = userEntityService.getByUsernameOrEmail(entryUser.getUsername(), entryUser.getEmail());
    var password = userEntityService.getPasswordByUserId(user.getId());

    if (passwordEncoder.matches(entryUser.getPassword(), password)) {
      // Генерируем JWT-токен для авторизованного пользователя
      return jwtService.generateToken(user);
    }

    throw new InvalidCredentialsException();
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = userEntityService.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    user.setPassword(userEntityService.getPasswordByUserId(user.getId()));
    var userAuth = t().dtoToEntity(user);
    return userAuth;
  }

  @Transactional
  public Optional<UserDto> findByUsernameDto(String username) {
    return userEntityService.findByUsername(username);
  }

  @Override
  public Transformer<UserDto, UserForAuth> t() {
    return userForAuthTransformer;
  }

  public boolean hasPermission(UserDetails userDetails, EntityName entityName, AccessLevel accessLevel) {
    // Находим пользователя по его имени
    var user = userEntityService.findByUsername(userDetails.getUsername())
        .orElse(null);

    if (user == null) {
      return false;
    }

    // Проходим по ролям пользователя и проверяем права
    return user.getRoles().stream()
        .flatMap(role -> role.getPermissions().stream())
        .anyMatch(permission -> permission.getEntityName() == entityName && permission.getAccessLevel() == accessLevel);
  }
}

