package org.example.task_management_system.core.exception;


import base.constants.entity.EntityName;


public class EntityNotFoundException extends RuntimeException {


  public EntityNotFoundException(EntityName entity) {
    super("Not found %s".formatted(entity));
  }
}
