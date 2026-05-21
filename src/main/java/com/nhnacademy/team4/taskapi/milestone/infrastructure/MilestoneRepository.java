package com.nhnacademy.team4.taskapi.milestone.infrastructure;

import com.nhnacademy.team4.taskapi.milestone.domain.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface MilestoneRepository extends JpaRepository<Milestone,Long> {
    List<Milestone> findByProject_Id(Long projectId);

    boolean existsByProject_IdAndName(Long projectId, String name);

    Optional<Milestone> findByProject_IdAndId(Long projectId, Long id);
}
