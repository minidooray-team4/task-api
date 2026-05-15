package com.nhnacademy.team4.taskapi.task.infrastructure;

import com.nhnacademy.team4.taskapi.task.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
}
