package org.example.task_management_system.task_management.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDto {

  private Long id; // Идентификатор комментария
  private Long taskId; // Идентификатор задачи, к которой относится комментарий
  private String body; // Текст комментария
}