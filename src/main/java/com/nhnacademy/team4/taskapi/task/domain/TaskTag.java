package com.nhnacademy.team4.taskapi.task.domain;

import com.nhnacademy.team4.taskapi.tag.domain.Tag;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "task_tags")
public class TaskTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @JoinColumn(name = "task_id",nullable = false)
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id",insertable = false,updatable = false)
    @JoinColumn(name = "tag_id",nullable = false)
    private Tag tag;

    @Builder
    private TaskTag(Task task,Tag tag){
        this.task=task;
        this.tag=tag;
    }

    public static TaskTag create(Task task,Tag tag){
        return TaskTag.builder()
                .task(task)
                .tag(tag)
                .build();
    }

}
