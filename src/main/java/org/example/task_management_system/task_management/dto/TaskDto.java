package org.example.task_management_system.task_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

  private Long id;

  private String title;

  private String body;

  private Long executorId;

  private String status;

  private String priority;
}

