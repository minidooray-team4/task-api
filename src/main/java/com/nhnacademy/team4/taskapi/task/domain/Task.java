package com.nhnacademy.team4.taskapi.task.domain;

import com.nhnacademy.team4.taskapi.common.domain.BaseTimeEntity;
import com.nhnacademy.team4.taskapi.global.exception.BusinessException;
import com.nhnacademy.team4.taskapi.global.exception.ErrorCode;
import com.nhnacademy.team4.taskapi.milestone.domain.Milestone;
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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;


    @Column(name = "milestone_id")
    private Long milestoneId;

    /**
     * milestone FK
     * <p>
     * DB 복합 FK:
     * (project_id, milestone_id)
     * -> milestones(project_id, id)
     * <p>
     * project_id 컬럼은 Project 연관관계가 관리하고 있으므로
     * 여기서는 읽기 전용(insertable/updatable=false) 처리
     */
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
    private Milestone milestone;

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
            Milestone milestone,
            Project project,
            Long writerMemberId
    ) {
        this.title = title;
        this.content = content;
        this.milestone = milestone;
        this.project = project;
        this.writerMemberId = writerMemberId;
    }

    public static Task create(
            String title,
            String content,
            Milestone milestone,
            Project project,
            Long writerMemberId
    ) {
        return Task.builder()
                .title(title)
                .content(content)
                .milestone(milestone)
                .project(project)
                .writerMemberId(writerMemberId)
                .build();
    }

    public Long getMilestoneId() {
        return milestone == null ? null : milestone.getId();
    }

    public Long getProjectId() {
        return project.getId();
    }

    public void update(String title, String content) {
        if (title != null) {
            if (title.isBlank()) {
                throw new BusinessException(ErrorCode.INVALID_REQUEST);
            }
            this.title = title;
        }

        if (content != null) {
            this.content = content;
        }
    }

    public void assignMilestone(Milestone milestone) {
        this.milestone = milestone;
        this.milestoneId = milestone.getId();
    }

    public void detachMilestone() {
        this.milestone = null;
        this.milestoneId = null;
    }
}