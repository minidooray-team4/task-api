package com.nhnacademy.team4.taskapi.task.domain;

import com.nhnacademy.team4.taskapi.common.domain.BaseTimeEntity;
import com.nhnacademy.team4.taskapi.milestone.domain.MileStone;
import com.nhnacademy.team4.taskapi.project.domain.Project;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tasks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // project FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    // 복합 FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(
                    name = "project_id",
                    referencedColumnName = "project_id",
                    insertable = false,
                    updatable = false
            ),
            @JoinColumn(
                    name = "milestone_id",
                    referencedColumnName = "id",
                    insertable = false,
                    updatable = false
            )
    })
    private MileStone milestone;


    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "writer_member_id", nullable = false)
    private Long writerMemberId;

    @Builder
    private Task(
            String title,
            String content,
            MileStone milestone,
            Project project,
            Long writerMemberId
    ) {
        this.title = title;
        this.content = content;
        this.milestone = milestone;
        this.project = project;
        this.writerMemberId = writerMemberId;
    }

    public static Task create(String title, String content, MileStone milestone, Project project, Long writerMemberId) {
        return Task.builder()
                .title(title)
                .content(content)
                .milestone(milestone)
                .project(project)
                .writerMemberId(writerMemberId)
                .build();
    }

    public Long getMilestoneId() {
        return milestone.getId();
    }
}