package org.example.task_management_system.security.service_model;

import base.repository.JpaSpecificationExecutorRepository;
import base.service.CreateService;
import base.service.DeleteService;
import base.service.ReadService;
import base.service.UpdateService;
import base.transformer.Transformer;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.example.task_management_system.security.dto.RoleDto;
import org.example.task_management_system.security.entity.RoleEntity;
import org.example.task_management_system.security.repository.RoleRepository;
import org.example.task_management_system.security.transformer.RoleEntityTransformer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class RoleEntityService implements
    CreateService<RoleDto, RoleEntity, Long>,
    ReadService<RoleDto, RoleEntity, Long>,
    UpdateService<RoleDto, RoleEntity, Long>,
    DeleteService<RoleDto, RoleEntity, Long> {

  private final RoleRepository roleRepository;

  private final RoleEntityTransformer roleEntityTransformer;

  @Override
  public JpaSpecificationExecutorRepository<RoleEntity, Long> repo() {
    return roleRepository;
  }

  @Override
  public Transformer<RoleDto, RoleEntity> t() {
    return roleEntityTransformer;
  }

  @Transactional
  public Optional<RoleDto> findByName(String name) {
    return roleRepository.findByName(name).map(e -> t().entityToDto(e));
  }
}
