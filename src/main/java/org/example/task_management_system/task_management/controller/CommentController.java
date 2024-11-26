package org.example.task_management_system.task_management.controller;

import base.controller.crud.CreateController;
import base.controller.crud.DeleteController;
import base.controller.crud.ReadController;
import base.controller.crud.UpdateController;
import base.controller.crudown.CreateOwnController;
import base.controller.crudown.ReadOwnController;
import base.service.abstractions.BaseOwnService;
import base.service.abstractions.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.example.task_management_system.task_management.business_entity.Comment;
import org.example.task_management_system.task_management.dto.CommentDto;
import org.example.task_management_system.task_management.entity.CommentEntity;
import org.example.task_management_system.task_management.service.CommentOwnService;
import org.example.task_management_system.task_management.service_model.CommentEntityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static base.constants.entity.EntityNames.COMMENT;


/**
 * Контроллер для управления комментариями.
 * <p>
 * Предоставляет API для создания, чтения, обновления и удаления комментариев,
 * а также для операций, зависящих от прав пользователя.
 *
 * @author Ivan Zhendorenko
 */
@AllArgsConstructor
@RestController
@RequestMapping("api/" + COMMENT)
@Tag(name = "Comments", description = "API для управления комментариями")
public class CommentController implements
    CreateController<CommentDto, Comment, Long>,
    ReadController<CommentDto, Comment, Long>,
    UpdateController<CommentDto, Comment, Long>,
    DeleteController<CommentDto, Comment, Long>,
    ReadOwnController<CommentDto, Comment, Long>,
    CreateOwnController<CommentDto, Comment, Long> {

  private final CommentEntityService commentEntityService;
  private final CommentOwnService commentOwnService;

  /**
   * Cервис для базовых операций с комментариями.
   *
   * @return объект {@link BaseService}, обеспечивающий операции CRUD над комментариями.
   */
  @Override
  public BaseService<CommentDto, CommentEntity> svc() {
    return commentEntityService;
  }

  /**
   * Cервис для операций, зависящих от прав пользователя.
   *
   * @return объект {@link BaseOwnService}, обеспечивающий операции с учетом прав текущего пользователя.
   */
  @Override
  @Operation(summary = "Получение сервиса с учетом прав", description = "Возвращает сервис для операций, зависящих от прав текущего пользователя.")
  public BaseOwnService<CommentDto, ? extends Comment> svcOwn() {
    return commentOwnService;
  }
}
