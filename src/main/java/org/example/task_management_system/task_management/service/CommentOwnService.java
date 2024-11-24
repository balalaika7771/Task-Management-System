package org.example.task_management_system.task_management.service;

import base.service.abstractions.BaseService;
import base.service.crudown.CreateOwnService;
import base.service.crudown.ReadOwnService;
import lombok.AllArgsConstructor;
import org.example.task_management_system.task_management.dto.CommentDto;
import org.example.task_management_system.task_management.entity.CommentEntity;
import org.example.task_management_system.task_management.service_model.CommentEntityService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CommentOwnService implements
    ReadOwnService<CommentDto, CommentEntity, Long>,
    CreateOwnService<CommentDto, CommentEntity, Long> {

  private final CommentEntityService commentEntityService;
  private final TaskOwnService taskOwnService;

  /**
   * Создаёт спецификацию для фильтрации комментариев текущего пользователя
   *
   * @param currentUserId ID текущего пользователя
   * @return Спецификация для фильтрации комментариев
   */
  private Specification<CommentEntity> filterCommentsByUser(Long currentUserId) {
    return (root, query, cb) -> root.get("taskId").in(
        taskOwnService.readFilterByUserId(currentUserId)
    );
  }

  @Override
  public BaseService<CommentDto, ? extends CommentEntity> svc() {
    return commentEntityService;
  }

  @Override
  public void checkCreateAccess(CommentDto commentDto, Long currentUserId) {
    // Проверяем, имеет ли пользователь доступ к задаче, связанной с комментарием
    if (commentDto.getTaskId() == null) {
      throw new IllegalArgumentException("Task ID cannot be null for creating a comment.");
    }

    taskOwnService.checkTaskAccess(commentDto.getTaskId(), currentUserId);
  }

  @Override
  public Specification<CommentEntity> readFilterByUserId(Long currentUserId) {
    return filterCommentsByUser(currentUserId);
  }
}
