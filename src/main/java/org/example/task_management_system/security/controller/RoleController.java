package org.example.task_management_system.security.controller;

import base.controller.crud.CrudController;
import base.service.CrudService;
import lombok.AllArgsConstructor;
import org.example.task_management_system.security.business_entity.Role;
import org.example.task_management_system.security.dto.RoleDto;
import org.example.task_management_system.security.entity.RoleEntity;
import org.example.task_management_system.security.service_model.RoleEntityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static base.constants.entity.EntityNames.ROLE;


@AllArgsConstructor
@RestController
@RequestMapping("api/" + ROLE)
public class RoleController implements CrudController<RoleDto, Role, Long> {

  private final RoleEntityService roleEntityService;

  @Override
  public CrudService<RoleDto, RoleEntity, Long> svc() {
    return roleEntityService;
  }
}
