package org.example.task_management_system.task_management.business_entity;

public enum Priority {
  HIGH("Высокий"),
  MEDIUM("Средний"),
  LOW("Низкий");

  private final String description;

  // Конструктор для хранения описания
  Priority(String description) {
    this.description = description;
  }

  // Метод для получения описания приоритета
  public String getDescription() {
    return description;
  }
}

