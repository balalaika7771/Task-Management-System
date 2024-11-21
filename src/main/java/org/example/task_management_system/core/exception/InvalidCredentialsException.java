package org.example.task_management_system.core.exception;

import lombok.Getter;


@Getter
public class InvalidCredentialsException extends RuntimeException {

  public InvalidCredentialsException() {
    super("Invalid credentials");
  }
}
