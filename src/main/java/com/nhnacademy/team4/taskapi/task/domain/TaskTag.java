package com.nhnacademy.team4.taskapi.task.domain;

import com.nhnacademy.team4.taskapi.tags.domain.Tag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "task_tags")
public class TaskTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id",nullable = false)
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id",nullable = false)
    private Tag tag;

    @Column(name = "project_id",nullable = false)
    private Long projectId;

    @Builder
    private TaskTag(Task task,Tag tag,Long projectId){
        this.task=task;
        this.tag=tag;
        this.projectId=projectId;
    }

    public static TaskTag create(Task task,Tag tag,Long projectId){
        return TaskTag.builder()
                .task(task)
                .tag(tag)
                .projectId(projectId)
                .build();
    }

}
