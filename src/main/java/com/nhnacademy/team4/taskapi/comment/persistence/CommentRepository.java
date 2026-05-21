package com.nhnacademy.team4.taskapi.comment.persistence;


import com.nhnacademy.team4.taskapi.comment.domain.Comment;
import com.nhnacademy.team4.taskapi.task.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTask_Id(Long taskId);

    Optional<Comment> findByWriterMemberIdAndTask_Id(Long writerMemberId, Long task_id);
}
