package org.example.task_management_system.core.controller;

import base.controller.crud.CrudController;
import base.service.CrudService;
import lombok.AllArgsConstructor;
import org.example.task_management_system.core.business_entity.Role;
import org.example.task_management_system.core.dto.RoleDto;
import org.example.task_management_system.core.entity.RoleEntity;
import org.example.task_management_system.core.service_model.RoleEntityService;
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
