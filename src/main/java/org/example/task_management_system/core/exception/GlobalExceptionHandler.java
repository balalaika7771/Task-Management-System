package org.example.task_management_system.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(InvalidCredentialsException.class)
  public ResponseEntity<SimpleErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException ex) {
    SimpleErrorResponse errorResponse = new SimpleErrorResponse(ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<SimpleErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
    SimpleErrorResponse errorResponse = new SimpleErrorResponse(ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(NameExistsException.class)
  public ResponseEntity<SimpleErrorResponse> handleEntityNameExistsException(NameExistsException ex) {
    SimpleErrorResponse errorResponse = new SimpleErrorResponse(ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(EmailExistsException.class)
  public ResponseEntity<SimpleErrorResponse> handleEntityEmailExistsException(EmailExistsException ex) {
    SimpleErrorResponse errorResponse = new SimpleErrorResponse(ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(SecurityException.class)
  public ResponseEntity<SimpleErrorResponse> handleEntitySecurityException(SecurityException ex) {
    SimpleErrorResponse errorResponse = new SimpleErrorResponse(ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.LOCKED);
  }

}
