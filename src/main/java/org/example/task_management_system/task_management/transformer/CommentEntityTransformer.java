package org.example.task_management_system.task_management.transformer;

import base.transformer.Transformer;
import org.example.task_management_system.task_management.dto.CommentDto;
import org.example.task_management_system.task_management.entity.CommentEntity;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;


@Mapper(componentModel = SPRING)
public interface CommentEntityTransformer extends Transformer<CommentDto, CommentEntity> {

  CommentDto entityToDto(CommentEntity entity);

  CommentEntity dtoToEntity(CommentDto dto);
}
