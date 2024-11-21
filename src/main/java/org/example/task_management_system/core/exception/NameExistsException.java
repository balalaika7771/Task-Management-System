package org.example.task_management_system.core.exception;


public class NameExistsException extends RuntimeException {

  public NameExistsException(String name) {
    super("Name %s already exist".formatted(name));
  }
}
