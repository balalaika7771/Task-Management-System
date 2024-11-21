package org.example.task_management_system.core.business_entity;


import base.abstractions.Identifiable;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
public class User extends Identifiable<User> {

  private String username;

  private String email;
}
