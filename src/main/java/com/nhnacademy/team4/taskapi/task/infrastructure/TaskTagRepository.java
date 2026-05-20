package com.nhnacademy.team4.taskapi.task.infrastructure;


import com.nhnacademy.team4.taskapi.task.domain.TaskTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskTagRepository extends JpaRepository<TaskTag,Long> {
    void deleteByTaskIdAndTagId(Long taskId,Long tagId);

    List<TaskTag> findByTaskId(Long taskId);
}
