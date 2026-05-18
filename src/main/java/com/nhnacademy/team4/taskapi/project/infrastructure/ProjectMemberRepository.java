package com.nhnacademy.team4.taskapi.project.infrastructure;

import com.nhnacademy.team4.taskapi.project.domain.ProjectMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectMemberRepository extends JpaRepository<ProjectMembers, Long> {
    boolean existsByProject_IdAndMemberId(
            Long projectId,
            Long memberId
    );

    @Query("""
                select pm
                from ProjectMembers pm
                join fetch pm.project
                where pm.memberId = :memberId
            """)
    List<ProjectMembers> findByMemberId(Long memberId);
}
