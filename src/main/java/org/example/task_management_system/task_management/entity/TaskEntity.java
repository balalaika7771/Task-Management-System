package org.example.task_management_system.task_management.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.example.task_management_system.task_management.business_entity.Task;


@Entity
@Table(name = "tasks")
@Getter
@Setter
@AttributeOverrides({
    @AttributeOverride(name = "title", column = @Column(name = "title", nullable = false)),
    @AttributeOverride(name = "body", column = @Column(name = "body", nullable = false)),
    @AttributeOverride(name = "executorId", column = @Column(name = "executor_id")),
    @AttributeOverride(name = "status", column = @Column(name = "status", nullable = false)),
    @AttributeOverride(name = "priority", column = @Column(name = "priority", nullable = false))
})
public class TaskEntity extends Task {

}

