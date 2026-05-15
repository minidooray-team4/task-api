package com.nhnacademy.team4.taskapi.task.domain;


import com.nhnacademy.team4.taskapi.common.domain.BaseTimeEntity;
import com.nhnacademy.team4.taskapi.project.domain.Project;
import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "tasks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task extends BaseTimeEntity {

    @Id
    private Long id;

    @JoinColumn(name = "project_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @Column(name = "milestone_id")
    private Long milestoneId;

    @Column(nullable = false, length = 200)
    private String title;

    private String content;

    @Column(name = "writer_member_id",nullable = false)
    private Long writerMemberId;

}


