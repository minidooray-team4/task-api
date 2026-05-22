package com.nhnacademy.team4.taskapi.task.infrastructure;

import com.nhnacademy.team4.taskapi.task.domain.TaskTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskTagRepository extends JpaRepository<TaskTag,Long> {
    int deleteByTask_IdAndTag_Id(Long taskId,Long tagId);
    boolean existsByTask_IdAndTag_Id(Long taskId,Long tagId);
    void deleteByTaskIdAndTagId(Long taskId,Long tagId);

    @Query("select tt from TaskTag tt join fetch tt.tag where tt.task.id = :taskId") // n+1 문제 해결
    List<TaskTag> findByTask_Id(Long taskId);

    void deleteByTask_Id(Long taskId);
}
