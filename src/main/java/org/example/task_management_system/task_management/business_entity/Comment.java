package org.example.task_management_system.task_management.business_entity;

import base.abstractions.Identifiable;
import base.abstractions.Owned;
import base.constants.entity.AccessLevel;
import jakarta.persistence.MappedSuperclass;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
public class Comment extends Identifiable<Comment> implements Owned {

  Long ownerId;

  String body;

  @Override
  public Set<Long> getAccessibleUserIds(AccessLevel level) {
    return Set.of();
  }
}
