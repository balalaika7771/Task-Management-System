package org.example.task_management_system.core.transformer;

import base.transformer.Transformer;
import org.example.task_management_system.core.dto.UserDto;
import org.example.task_management_system.core.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;


@Mapper(componentModel = SPRING, uses = {RoleEntityTransformer.class, PermissionEntityTransformer.class})
public interface UserEntityTransformer extends Transformer<UserDto, UserEntity> {

  @Mapping(target = "password", ignore = true)
  @Mapping(source = "roles", target = "roles")
  UserDto entityToDto(UserEntity userEntity);

  @Mapping(source = "roles", target = "roles")
  UserEntity dtoToEntity(UserDto userDto);
}

