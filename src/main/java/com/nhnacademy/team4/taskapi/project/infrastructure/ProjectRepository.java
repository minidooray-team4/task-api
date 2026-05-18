package com.nhnacademy.team4.taskapi.project.infrastructure;

import com.nhnacademy.team4.taskapi.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
}
