package com.nhnacademy.team4.taskapi.task.application;


import com.nhnacademy.team4.taskapi.comment.application.result.CommentResult;
import com.nhnacademy.team4.taskapi.comment.persistence.CommentRepository;
import com.nhnacademy.team4.taskapi.global.exception.BusinessException;


import com.nhnacademy.team4.taskapi.global.exception.ErrorCode;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectMemberRepository;
import com.nhnacademy.team4.taskapi.tags.application.result.TagResult;
import com.nhnacademy.team4.taskapi.tags.persistence.TagRepository;
import com.nhnacademy.team4.taskapi.task.application.command.GetProjectTasksQuery;

import com.nhnacademy.team4.taskapi.task.application.result.TaskDetailResult;
import com.nhnacademy.team4.taskapi.task.application.result.TaskSummaryResult;

import com.nhnacademy.team4.taskapi.task.domain.Task;
import com.nhnacademy.team4.taskapi.task.domain.TaskTag;
import com.nhnacademy.team4.taskapi.task.infrastructure.TaskRepository;

import com.nhnacademy.team4.taskapi.task.infrastructure.TaskTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.nhnacademy.team4.taskapi.global.exception.ErrorCode.TASK_NOT_FOUND;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TaskQueryService {

    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;
    private final TaskTagRepository taskTagRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final CommentRepository commentRepository;

    public List<TaskSummaryResult> getProjectTasks(GetProjectTasksQuery query) {
        //미구현
        return List.of();
    }


    public TaskDetailResult getTaskDetail(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException(TASK_NOT_FOUND));

        validateProjectAccess(task.getProjectId(), task.getWriterMemberId());

        List<TagResult> tagList = taskTagRepository.findByTaskId(taskId)
                .stream()
                .map(TaskTag::getTag)
                .map(TagResult::from)
                .toList();

        List<CommentResult> commentList = commentRepository.findByTaskId(taskId)
                .stream()
                .map(CommentResult::from)
                .toList();

        return TaskDetailResult.from(task, tagList, commentList);

    }

    private void validateProjectAccess(Long projectId, Long memberId) {
        boolean isMember = projectMemberRepository.existsByProjectIdAndMemberId(projectId, memberId);

        if (!isMember) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }
    }
}
