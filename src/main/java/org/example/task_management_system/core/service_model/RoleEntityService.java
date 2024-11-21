package org.example.task_management_system.core.service_model;

import base.repository.JpaSpecificationExecutorRepository;
import base.service.CrudService;
import base.transformer.Transformer;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.example.task_management_system.core.dto.RoleDto;
import org.example.task_management_system.core.entity.RoleEntity;
import org.example.task_management_system.core.repository.RoleRepository;
import org.example.task_management_system.core.transformer.RoleEntityTransformer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class RoleEntityService implements CrudService<RoleDto, RoleEntity, Long> {

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
