package org.example.task_management_system.core.controller;

import base.controller.crud.CrudController;
import base.service.CrudService;
import lombok.AllArgsConstructor;
import org.example.task_management_system.core.business_entity.User;
import org.example.task_management_system.core.dto.UserDto;
import org.example.task_management_system.core.entity.UserEntity;
import org.example.task_management_system.core.service_model.UserEntityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static base.constants.entity.EntityNames.USER;


@AllArgsConstructor
@RestController
@RequestMapping("api/" + USER)
public class UserController implements CrudController<UserDto, User, Long> {

  private final UserEntityService userEntityService;

  @Override
  public CrudService<UserDto, UserEntity, Long> svc() {
    return userEntityService;
  }


}
