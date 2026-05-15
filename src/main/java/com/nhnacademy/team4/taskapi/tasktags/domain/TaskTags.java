package com.nhnacademy.team4.taskapi.tasktags.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "task_tags")
public class TaskTags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "task_id",nullable = false)
    private Long taskId;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "tag_id",nullable = false)
    private Long tagId;
}
