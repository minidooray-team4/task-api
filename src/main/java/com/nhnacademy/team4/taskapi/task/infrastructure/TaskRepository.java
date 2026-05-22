package com.nhnacademy.team4.taskapi.task.infrastructure;


import com.nhnacademy.team4.taskapi.task.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    @Query("select t from Task t join fetch t.milestone where t.project.id = :projectId") // N+1 문제 방지
    List<Task> findByProject_Id(Long projectId);
}
