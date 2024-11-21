package org.example.task_management_system.core.transformer;

import base.transformer.Transformer;
import org.example.task_management_system.core.dto.PermissionDto;
import org.example.task_management_system.core.entity.PermissionEntity;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;


@Mapper(componentModel = SPRING)
public interface PermissionEntityTransformer extends Transformer<PermissionDto, PermissionEntity> {

  PermissionDto entityToDto(PermissionEntity role);

  PermissionEntity dtoToEntity(PermissionDto roleDto);

}
