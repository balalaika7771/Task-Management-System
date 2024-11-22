package org.example.task_management_system.security.transformer;

import base.transformer.Transformer;
import org.example.task_management_system.security.dto.PermissionDto;
import org.example.task_management_system.security.entity.PermissionEntity;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;


@Mapper(componentModel = SPRING)
public interface PermissionEntityTransformer extends Transformer<PermissionDto, PermissionEntity> {

  PermissionDto entityToDto(PermissionEntity role);

  PermissionEntity dtoToEntity(PermissionDto roleDto);

}
