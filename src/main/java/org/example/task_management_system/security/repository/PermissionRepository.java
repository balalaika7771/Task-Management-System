package org.example.task_management_system.security.repository;

import base.repository.JpaSpecificationExecutorRepository;
import org.example.task_management_system.security.entity.PermissionEntity;


public interface PermissionRepository extends JpaSpecificationExecutorRepository<PermissionEntity, Long> {

}
