package org.example.task_management_system.task_management.business_entity;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * Статусы задач.
 *
 * @author Zhendorenko Ivan
 */
@AllArgsConstructor
@Getter
public enum TaskStatus {
  PENDING("В ожидании"),      // Задача запланирована, но еще не начата
  IN_PROGRESS("В процессе"),  // Задача выполняется
  COMPLETED("Завершено"),     // Задача завершена
  CANCELED("Отменено");       // Задача отменена

  private final String description;
}
