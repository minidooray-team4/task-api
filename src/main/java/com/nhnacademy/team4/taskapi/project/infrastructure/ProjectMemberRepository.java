package com.nhnacademy.team4.taskapi.project.infrastructure;

import com.nhnacademy.team4.taskapi.project.domain.ProjectMembers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberRepository extends JpaRepository<ProjectMembers, Long> {
    boolean existsByProject_IdAndMemberId(
            Long projectId,
            Long memberId
    );
}
