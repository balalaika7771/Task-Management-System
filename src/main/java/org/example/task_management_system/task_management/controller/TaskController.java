package org.example.task_management_system.task_management.controller;

import base.controller.crud.CreateController;
import base.controller.crud.DeleteController;
import base.controller.crud.ReadController;
import base.controller.crud.UpdateController;
import base.controller.crudown.ReadOwnController;
import base.service.abstractions.BaseOwnService;
import base.service.abstractions.BaseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.example.task_management_system.task_management.business_entity.Task;
import org.example.task_management_system.task_management.dto.TaskDto;
import org.example.task_management_system.task_management.entity.TaskEntity;
import org.example.task_management_system.task_management.service.TaskOwnService;
import org.example.task_management_system.task_management.service_model.TaskEntityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static base.constants.entity.EntityNames.TASK;


/**
 * Контроллер для управления задачами.
 * <p>
 * Предоставляет API для создания, чтения, обновления и удаления задач,
 * а также для операций, зависящих от прав пользователя.
 *
 * @author Ivan Zhendorenko
 */
@AllArgsConstructor
@RestController
@RequestMapping("api/" + TASK)
@Tag(name = "Tasks", description = "API для управления задачами")
public class TaskController implements
    CreateController<TaskDto, Task, Long>,
    ReadController<TaskDto, Task, Long>,
    UpdateController<TaskDto, Task, Long>,
    DeleteController<TaskDto, Task, Long>,
    ReadOwnController<TaskDto, Task, Long> {

  private final TaskEntityService taskEntityService;
  private final TaskOwnService taskOwnService;

  /**
   * Cервис для базовых операций с задачами.
   *
   * @return объект {@link BaseService}, обеспечивающий операции CRUD над задачами.
   */
  @Override
  public BaseService<TaskDto, TaskEntity> svc() {
    return taskEntityService;
  }

  /**
   * Cервис для операций, зависящих от прав пользователя.
   *
   * @return объект {@link BaseOwnService}, обеспечивающий операции с учетом прав текущего пользователя.
   */
  @Override
  public BaseOwnService<TaskDto, ? extends Task> svcOwn() {
    return taskOwnService;
  }
}
