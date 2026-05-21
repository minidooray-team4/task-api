package com.nhnacademy.team4.taskapi.project.infrastructure;

import com.nhnacademy.team4.taskapi.project.domain.Project;
import com.nhnacademy.team4.taskapi.project.domain.ProjectMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectMemberRepository extends JpaRepository<ProjectMembers, Long> {
    boolean existsByProjectIdAndMemberId(
            Long projectId,
            Long memberId
    );

    @Query("""
    select pm.project
    from ProjectMembers pm
    where pm.memberId = :memberId
""")
    List<Project> findProjectsByMemberId(Long memberId);

    List<ProjectMembers> findByProject_Id(Long projectId);

}

