package org.example.task_management_system.core.repository;

import base.repository.JpaSpecificationExecutorRepository;
import java.util.Optional;
import org.example.task_management_system.core.entity.UserEntity;


public interface UserRepository extends JpaSpecificationExecutorRepository<UserEntity, Long> {

  Optional<UserEntity> findByUsername(String username);

  Optional<UserEntity> findByEmail(String email);
}
