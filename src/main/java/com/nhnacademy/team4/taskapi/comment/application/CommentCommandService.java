package com.nhnacademy.team4.taskapi.comment.application;

import com.nhnacademy.team4.taskapi.comment.application.command.CreateCommentCommand;
import com.nhnacademy.team4.taskapi.comment.application.command.UpdateCommentCommand;
import com.nhnacademy.team4.taskapi.comment.domain.Comment;
import com.nhnacademy.team4.taskapi.comment.persistence.CommentRepository;
import com.nhnacademy.team4.taskapi.global.exception.BusinessException;
import com.nhnacademy.team4.taskapi.global.exception.ErrorCode;
import com.nhnacademy.team4.taskapi.task.domain.Task;
import com.nhnacademy.team4.taskapi.task.infrastructure.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentCommandService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;

    public void createComment(CreateCommentCommand command) {
        Task task = taskRepository.findById(command.taskId())
                .orElseThrow(() -> new BusinessException(ErrorCode.TASK_NOT_FOUND));


        Comment comment = Comment.create(task,command.content(),command.requesterMemberId());
        commentRepository.save(comment);

    }

    public void updateComment(UpdateCommentCommand command) {
        Comment comment = commentRepository.findById(command.commentId())
                        .orElseThrow(()-> new BusinessException(ErrorCode.COMMENT_NOT_FOUND));
        comment.update(command.content());
    }

    public void deleteComment(Long commentId, Long requesterMemberId) {


        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new BusinessException(ErrorCode.COMMENT_NOT_FOUND));

        commentRepository.delete(comment);

    }




}
