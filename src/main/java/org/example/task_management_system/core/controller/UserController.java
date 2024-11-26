package org.example.task_management_system.core.controller;

import base.controller.crud.CreateController;
import base.controller.crud.DeleteController;
import base.controller.crud.ReadController;
import base.controller.crud.UpdateController;
import base.service.abstractions.BaseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.example.task_management_system.core.business_entity.User;
import org.example.task_management_system.core.dto.UserDto;
import org.example.task_management_system.core.entity.UserEntity;
import org.example.task_management_system.core.service_model.UserEntityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static base.constants.entity.EntityNames.USER;


/**
 * Контроллер для управления пользователями.
 * <p>
 * Предоставляет API для создания, чтения, обновления и удаления пользователей.
 *
 * @author Ivan Zhendorenko
 */
@AllArgsConstructor
@RestController
@RequestMapping("api/" + USER)
@Tag(name = "Users", description = "API для управления пользователями")
public class UserController implements
    CreateController<UserDto, User, Long>,
    ReadController<UserDto, User, Long>,
    UpdateController<UserDto, User, Long>,
    DeleteController<UserDto, User, Long> {

  private final UserEntityService userEntityService;

  /**
   * Возвращает сервис для работы с сущностями пользователей.
   *
   * @return объект {@link BaseService}, обеспечивающий операции с пользователями.
   */
  @Override
  public BaseService<UserDto, UserEntity> svc() {
    return userEntityService;
  }
}
