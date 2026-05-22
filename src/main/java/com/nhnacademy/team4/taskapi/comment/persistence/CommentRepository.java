package com.nhnacademy.team4.taskapi.comment.persistence;


import com.nhnacademy.team4.taskapi.comment.domain.Comment;
import com.nhnacademy.team4.taskapi.task.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTask_Id(Long taskId);

    @Query("select c from Comment c join fetch c.task where c.id = :commentId")
    Optional<Comment> findWithTaskById(@Param("commentId") Long commentId);
}
