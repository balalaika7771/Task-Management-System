package org.example.task_management_system.task_management.business_entity;

import base.abstractions.Identifiable;
import base.abstractions.Owned;
import base.constants.entity.AccessLevel;
import jakarta.persistence.MappedSuperclass;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
public class Task extends Identifiable<Task> implements Owned {


  String title;

  String body;

  Long executorId;

  TaskStatus status;

  @Override
  public Set<Long> getAccessibleUserIds(AccessLevel level) {
    if (level.isRead()) {
      return Set.of(executorId).stream().filter(Objects::nonNull).collect(Collectors.toSet());
    }
    return Set.of();
  }
}
