package com.nhnacademy.team4.taskapi.milestone.domain;

import com.nhnacademy.team4.taskapi.common.domain.BaseTimeEntity;
import com.nhnacademy.team4.taskapi.global.exception.BusinessException;
import com.nhnacademy.team4.taskapi.global.exception.ErrorCode;
import com.nhnacademy.team4.taskapi.project.domain.Project;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(
        name = "milestones",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_milestones_project_id",
                        columnNames = {"project_id", "id"}
                )
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Milestone extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Builder
    private Milestone(Long id, Project project, String name, LocalDate dueDate) {
        this.id = id;
        this.project = project;
        this.name = name;
        this.dueDate = dueDate;

    }

    public static Milestone create(Project project, String name, LocalDate dueDate) {
        return Milestone.builder()
                .project(project)
                .name(name)
                .dueDate(dueDate)
                .build();
    }

    public Long getProjectId() {
        return project.getId();
    }

    public void update(String name, LocalDate dueDate,boolean clearDueDate) {
        if (name != null) {
            this.name = name;
        }

        // 모순 검증
        if (clearDueDate && dueDate != null) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST);
        }

        // dueDate 처리
        if (clearDueDate) {
            this.dueDate = null;
            return;
        }

        if (dueDate != null) {
            this.dueDate = dueDate;
        }

    }
}