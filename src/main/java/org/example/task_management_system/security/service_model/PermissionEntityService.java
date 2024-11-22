package org.example.task_management_system.security.service_model;

import base.repository.JpaSpecificationExecutorRepository;
import base.service.CrudService;
import base.transformer.Transformer;
import lombok.AllArgsConstructor;
import org.example.task_management_system.security.dto.PermissionDto;
import org.example.task_management_system.security.entity.PermissionEntity;
import org.example.task_management_system.security.repository.PermissionRepository;
import org.example.task_management_system.security.transformer.PermissionEntityTransformer;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class PermissionEntityService implements CrudService<PermissionDto, PermissionEntity, Long> {

  private PermissionRepository permissionRepository;

  private PermissionEntityTransformer permissionEntityTransformer;

  @Override
  public JpaSpecificationExecutorRepository<PermissionEntity, Long> repo() {
    return permissionRepository;
  }

  @Override
  public Transformer<PermissionDto, PermissionEntity> t() {
    return permissionEntityTransformer;
  }
}
