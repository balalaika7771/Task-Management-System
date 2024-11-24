package org.example.task_management_system.task_management.entity;


import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.example.task_management_system.task_management.business_entity.Comment;


@Entity
@Table(name = "comments")
@Getter
@Setter
@AttributeOverrides({
    @AttributeOverride(name = "taskId", column = @Column(name = "task_id", nullable = false)),
    @AttributeOverride(name = "body", column = @Column(name = "body", nullable = false))
})
public class CommentEntity extends Comment {

}