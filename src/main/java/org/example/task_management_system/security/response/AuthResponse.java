package org.example.task_management_system.security.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


@Data
@AllArgsConstructor
@Getter
public class AuthResponse {

  private String token;
  private String username;
}
