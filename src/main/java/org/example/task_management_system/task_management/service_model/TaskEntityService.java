package org.example.task_management_system.task_management.service_model;

import base.repository.JpaSpecificationExecutorRepository;
import base.service.CreateService;
import base.service.DeleteService;
import base.service.ReadService;
import base.service.UpdateService;
import base.transformer.Transformer;
import lombok.AllArgsConstructor;
import org.example.task_management_system.task_management.dto.TaskDto;
import org.example.task_management_system.task_management.entity.TaskEntity;
import org.example.task_management_system.task_management.repository.TaskRepository;
import org.example.task_management_system.task_management.transformer.TaskEntityTransformer;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class TaskEntityService implements
    CreateService<TaskDto, TaskEntity, Long>,
    ReadService<TaskDto, TaskEntity, Long>,
    UpdateService<TaskDto, TaskEntity, Long>,
    DeleteService<TaskDto, TaskEntity, Long> {

  private TaskRepository taskRepository;

  private TaskEntityTransformer transformer;

  @Override
  public JpaSpecificationExecutorRepository<TaskEntity, Long> repo() {
    return taskRepository;
  }

  @Override
  public Transformer<TaskDto, TaskEntity> t() {
    return transformer;
  }
}
