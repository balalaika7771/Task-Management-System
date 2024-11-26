package org.example.task_management_system.security.controller;

import base.controller.crud.CreateController;
import base.controller.crud.DeleteController;
import base.controller.crud.ReadController;
import base.controller.crud.UpdateController;
import base.service.abstractions.BaseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.example.task_management_system.security.business_entity.Role;
import org.example.task_management_system.security.dto.RoleDto;
import org.example.task_management_system.security.entity.RoleEntity;
import org.example.task_management_system.security.service_model.RoleEntityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static base.constants.entity.EntityNames.ROLE;


/**
 * Контроллер для управления ролями.
 * <p>
 * Предоставляет API для создания, чтения, обновления и удаления ролей.
 *
 * @author Ivan Zhendorenko
 */
@AllArgsConstructor
@RestController
@RequestMapping("api/" + ROLE)
@Tag(name = "Roles", description = "API для управления ролями пользователей")
public class RoleController implements
    CreateController<RoleDto, Role, Long>,
    ReadController<RoleDto, Role, Long>,
    UpdateController<RoleDto, Role, Long>,
    DeleteController<RoleDto, Role, Long> {

  private final RoleEntityService roleEntityService;

  /**
   * Возвращает сервис для работы с сущностями ролей.
   *
   * @return объект {@link BaseService} для управления ролями.
   */
  @Override
  public BaseService<RoleDto, RoleEntity> svc() {
    return roleEntityService;
  }
}
