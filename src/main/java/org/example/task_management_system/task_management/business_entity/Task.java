package org.example.task_management_system.task_management.business_entity;

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
public class Task extends Identifiable<Task> {

  String title;

  String body;

  Long executorId;

  TaskStatus status;

  Priority priority;

}
