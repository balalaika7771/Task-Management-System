package org.example.task_management_system.security.transformer;

import base.transformer.Transformer;
import org.example.task_management_system.core.dto.UserDto;
import org.example.task_management_system.security.business_entity.UserForAuth;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;


@Mapper(componentModel = SPRING)
public interface UserForAuthTransformer extends Transformer<UserDto, UserForAuth> {

  @Mapping(source = "roles", target = "roles")
  UserDto entityToDto(UserForAuth product);

  @Mapping(source = "roles", target = "roles")
  UserForAuth dtoToEntity(UserDto productDto);
}
