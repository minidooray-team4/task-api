package com.nhnacademy.team4.taskapi.task.application;


import com.nhnacademy.team4.taskapi.comment.application.result.CommentResult;
import com.nhnacademy.team4.taskapi.global.exception.BusinessException;

import com.nhnacademy.team4.taskapi.tag.application.result.TagResult;
import com.nhnacademy.team4.taskapi.task.application.command.GetProjectTasksQuery;
import com.nhnacademy.team4.taskapi.task.application.result.TaskDetailResult;
import com.nhnacademy.team4.taskapi.task.application.result.TaskSummaryResult;
import com.nhnacademy.team4.taskapi.task.application.usecase.GetProjectTasksUseCase;
import com.nhnacademy.team4.taskapi.task.application.usecase.GetTaskDetailUseCase;
import com.nhnacademy.team4.taskapi.task.domain.Task;
import com.nhnacademy.team4.taskapi.task.infrastructure.TaskRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.nhnacademy.team4.taskapi.global.exception.ErrorCode.TASK_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class TaskQueryService implements GetProjectTasksUseCase, GetTaskDetailUseCase {

    private final TaskRepository taskRepository;

    @Override
    public List<TaskSummaryResult> getProjectTasks(GetProjectTasksQuery query) {
        //미구현
        return List.of();
    }


    @Override
    public TaskDetailResult getTaskDetail(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException(TASK_NOT_FOUND));

        // NOTE: 아직 Tag,Comment 미구현, 추후 구현 예정
        List<TagResult> tagList = List.of();
        List<CommentResult> commentList = List.of();

        return TaskDetailResult.from(task, tagList, commentList);

    }
}
