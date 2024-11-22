package org.example.task_management_system.security.repository;

import base.repository.JpaSpecificationExecutorRepository;
import java.util.Optional;
import org.example.task_management_system.security.entity.RoleEntity;


public interface RoleRepository extends JpaSpecificationExecutorRepository<RoleEntity, Long> {

  Optional<RoleEntity> findByName(String name);

}
