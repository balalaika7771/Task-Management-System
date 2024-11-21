package org.example.task_management_system.core.exception;


public class EmailExistsException extends RuntimeException {

  public EmailExistsException(String email) {
    super("Email %s already exist".formatted(email));
  }
}
