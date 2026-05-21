package com.nhnacademy.team4.taskapi.comment.application;

import com.nhnacademy.team4.taskapi.comment.application.result.CommentResult;
import com.nhnacademy.team4.taskapi.comment.domain.Comment;
import com.nhnacademy.team4.taskapi.comment.persistence.CommentRepository;
import com.nhnacademy.team4.taskapi.global.exception.BusinessException;
import com.nhnacademy.team4.taskapi.global.exception.ErrorCode;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectMemberRepository;
import com.nhnacademy.team4.taskapi.task.domain.Task;
import com.nhnacademy.team4.taskapi.task.infrastructure.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentQueryService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final ProjectMemberRepository projectMemberRepository;

    public List<CommentResult> getComments(Long taskId,Long requesterMemberId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException(ErrorCode.TASK_NOT_FOUND));

        // 프로젝트멤버 인지 검증
        if(!projectMemberRepository.existsByProjectIdAndMemberId(task.getProjectId(),requesterMemberId)){
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        List<Comment> commentList = commentRepository.findByTask_Id(task.getId());
        return commentList.stream()
                .map(CommentResult::from)
                .toList();

    }

}
