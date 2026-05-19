package com.nhnacademy.team4.taskapi.milestone.infrastructure;

import com.nhnacademy.team4.taskapi.milestone.domain.MileStone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MilestoneRepository extends JpaRepository<MileStone,Long> {
    List<MileStone> findByProjectId(Long projectId);
}
