package org.example.task_management_system.task_management.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * DTO (Data Transfer Object) для сущности Task.
 *
 * @author Ivan Zhendorenko
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для представления задачи")
public class TaskDto {

  /**
   * Уникальный идентификатор задачи.
   */
  @Schema(description = "Уникальный идентификатор задачи", example = "1")
  private Long id;

  /**
   * Название задачи.
   */
  @Schema(description = "Название задачи", example = "Разработать API")
  private String title;

  /**
   * Описание или тело задачи.
   */
  @Schema(description = "Описание задачи", example = "Создать REST API для управления задачами")
  private String body;

  /**
   * ID исполнителя задачи.
   */
  @Schema(description = "ID исполнителя задачи", example = "101")
  private Long executorId;

  /**
   * Статус задачи.
   */
  @Schema(description = "Статус задачи", example = "IN_PROGRESS")
  private String status;

  /**
   * Приоритет задачи.
   */
  @Schema(description = "Приоритет задачи", example = "HIGH")
  private String priority;
}
