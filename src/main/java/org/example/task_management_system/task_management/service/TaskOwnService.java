package org.example.task_management_system.task_management.service;

import base.service.abstractions.BaseService;
import base.service.crudown.ReadOwnService;
import base.util.Data;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.experimental.ExtensionMethod;
import org.example.task_management_system.task_management.dto.TaskDto;
import org.example.task_management_system.task_management.entity.TaskEntity;
import org.example.task_management_system.task_management.service_model.TaskEntityService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@ExtensionMethod({Data.class})
public class TaskOwnService implements ReadOwnService<TaskDto, TaskEntity, Long> {

  private final TaskEntityService taskEntityService;

  /**
   * Проверяет доступ к задаче
   *
   * @param taskId        ID задачи
   * @param currentUserId ID текущего пользователя
   * @throws SecurityException если доступ запрещен
   */
  public void checkTaskAccess(Long taskId, Long currentUserId) {
    boolean hasAccess = taskEntityService
        .findByIdDto(taskId)
        .map(TaskDto::getExecutorId)
        .filter(Objects::nonNull)
        .filter(task -> task.equals(currentUserId))
        .isPresent();
    if (!hasAccess) {
      throw new SecurityException("Access denied to task with ID: " + taskId);
    }
  }

  /**
   * Создаёт спецификацию для фильтрации задач текущего пользователя
   *
   * @param currentUserId ID текущего пользователя
   * @return Спецификация для фильтрации задач
   */
  public Specification<TaskEntity> readFilterByUserId(Long currentUserId) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.equal(root.get("executorId"), currentUserId);
  }

  @Override
  public BaseService<TaskDto, ? extends TaskEntity> svc() {
    return taskEntityService;
  }
}

