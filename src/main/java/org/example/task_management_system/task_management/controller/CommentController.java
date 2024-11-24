package org.example.task_management_system.task_management.controller;

import base.controller.crud.CreateController;
import base.controller.crud.DeleteController;
import base.controller.crud.ReadController;
import base.controller.crud.UpdateController;
import base.controller.crudown.CreateOwnController;
import base.controller.crudown.ReadOwnController;
import base.service.abstractions.BaseOwnService;
import base.service.abstractions.BaseService;
import lombok.AllArgsConstructor;
import org.example.task_management_system.task_management.business_entity.Comment;
import org.example.task_management_system.task_management.dto.CommentDto;
import org.example.task_management_system.task_management.entity.CommentEntity;
import org.example.task_management_system.task_management.service.CommentOwnService;
import org.example.task_management_system.task_management.service_model.CommentEntityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static base.constants.entity.EntityNames.COMMENT;


@AllArgsConstructor
@RestController
@RequestMapping("api/" + COMMENT)
public class CommentController implements
    CreateController<CommentDto, Comment, Long>,
    ReadController<CommentDto, Comment, Long>,
    UpdateController<CommentDto, Comment, Long>,
    DeleteController<CommentDto, Comment, Long>,
    ReadOwnController<CommentDto, Comment, Long>,
    CreateOwnController<CommentDto, Comment, Long> {

  private final CommentEntityService commentEntityService;

  private final CommentOwnService commentOwnService;

  @Override
  public BaseService<CommentDto, CommentEntity> svc() {
    return commentEntityService;
  }


  @Override
  public BaseOwnService<CommentDto, ? extends Comment> svcOwn() {
    return commentOwnService;
  }
}
