package org.example.task_management_system.security.transformer;

import base.transformer.Transformer;
import org.example.task_management_system.security.dto.RoleDto;
import org.example.task_management_system.security.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;


@Mapper(componentModel = SPRING)
public interface RoleEntityTransformer extends Transformer<RoleDto, RoleEntity> {

  @Mapping(target = "permissions", source = "permissions")
  RoleDto entityToDto(RoleEntity roleEntity);

  @Mapping(target = "permissions", source = "permissions")
  RoleEntity dtoToEntity(RoleDto roleDto);
}
