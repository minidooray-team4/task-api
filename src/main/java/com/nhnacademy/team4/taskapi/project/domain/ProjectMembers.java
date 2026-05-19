package com.nhnacademy.team4.taskapi.project.domain;

import com.nhnacademy.team4.taskapi.common.domain.BaseCreatedAtEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "project_members",
        uniqueConstraints = {
                // 같은 프로젝트에 같은 멤버가 중복등록 방지
                @UniqueConstraint(
                        name = "uk_project_members_project_member",
                        columnNames = {"project_id", "member_id"}
                )
        }
)
public class ProjectMembers extends BaseCreatedAtEntity {

    @Builder
    private ProjectMembers(Project project, Long memberId) {
        this.project = project;
        this.memberId = memberId;
    }

    public static ProjectMembers create(Project project, Long memberId) {
        return ProjectMembers.builder()
                .project(project)
                .memberId(memberId)
                .build();
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "member_id", nullable = false)
    private Long memberId;
}
