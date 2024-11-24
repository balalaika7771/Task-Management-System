package org.example.task_management_system.task_management.repository;

import base.repository.JpaSpecificationExecutorRepository;
import org.example.task_management_system.task_management.entity.TaskEntity;


public interface TaskRepository extends JpaSpecificationExecutorRepository<TaskEntity, Long> {

}
