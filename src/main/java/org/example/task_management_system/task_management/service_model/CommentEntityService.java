package org.example.task_management_system.task_management.service_model;

import base.repository.JpaSpecificationExecutorRepository;
import base.service.CreateService;
import base.service.DeleteService;
import base.service.ReadService;
import base.service.UpdateService;
import base.transformer.Transformer;
import lombok.AllArgsConstructor;
import org.example.task_management_system.task_management.dto.CommentDto;
import org.example.task_management_system.task_management.entity.CommentEntity;
import org.example.task_management_system.task_management.repository.CommentRepository;
import org.example.task_management_system.task_management.transformer.CommentEntityTransformer;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CommentEntityService implements
    CreateService<CommentDto, CommentEntity, Long>,
    ReadService<CommentDto, CommentEntity, Long>,
    UpdateService<CommentDto, CommentEntity, Long>,
    DeleteService<CommentDto, CommentEntity, Long> {

  private CommentRepository commentRepository;

  private CommentEntityTransformer transformer;

  @Override
  public JpaSpecificationExecutorRepository<CommentEntity, Long> repo() {
    return commentRepository;
  }

  @Override
  public Transformer<CommentDto, CommentEntity> t() {
    return transformer;
  }
}
