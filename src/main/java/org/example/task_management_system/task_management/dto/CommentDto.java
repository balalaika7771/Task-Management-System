package org.example.task_management_system.task_management.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * DTO (Data Transfer Object) для сущности Comment.
 *
 * @author Ivan Zhendorenko
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "DTO для представления комментария")
public class CommentDto {

  /**
   * Уникальный идентификатор комментария.
   */
  @Schema(description = "Уникальный идентификатор комментария", example = "1")
  private Long id;

  /**
   * Идентификатор задачи, к которой относится комментарий.
   */
  @Schema(description = "Идентификатор задачи, к которой относится комментарий", example = "10")
  private Long taskId;

  /**
   * Текст комментария.
   */
  @Schema(description = "Текст комментария", example = "Эта задача требует дополнительных данных.")
  private String body;
}
