package org.example.task_management_system.task_management.transformer;

import base.transformer.Transformer;
import org.example.task_management_system.task_management.dto.TaskDto;
import org.example.task_management_system.task_management.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;


@Mapper(componentModel = SPRING)
public interface TaskEntityTransformer extends Transformer<TaskDto, TaskEntity> {

  @Mapping(source = "status.description", target = "status")
  @Mapping(source = "priority.description", target = "priority")
  TaskDto entityToDto(TaskEntity entity);

  @Mapping(target = "status", expression = "java(TaskStatus.valueOf(dto.getStatus()))")
  @Mapping(target = "priority", expression = "java(Priority.valueOf(dto.getPriority()))")
  TaskEntity dtoToEntity(TaskDto dto);
}
