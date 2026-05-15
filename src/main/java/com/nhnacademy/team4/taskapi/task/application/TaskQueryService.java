package com.nhnacademy.team4.taskapi.task.application;

import com.nhnacademy.team4.taskapi.task.application.command.GetProjectTasksQuery;
import com.nhnacademy.team4.taskapi.task.application.result.TaskDetailResult;
import com.nhnacademy.team4.taskapi.task.application.result.TaskResult;
import com.nhnacademy.team4.taskapi.task.application.result.TaskSummaryResult;
import com.nhnacademy.team4.taskapi.task.application.usecase.GetProjectTaskUseCase;
import com.nhnacademy.team4.taskapi.task.application.usecase.GetProjectTasksUseCase;
import com.nhnacademy.team4.taskapi.task.application.usecase.GetTaskDetailUseCase;

import java.util.List;

public class TaskQueryService implements GetProjectTasksUseCase , GetProjectTaskUseCase, GetTaskDetailUseCase {
    @Override
    public TaskDetailResult getProjectTask(GetProjectTaskUseCase command) {
        //미구현
        return null;
    }

    @Override
    public List<TaskSummaryResult> getProjectTasks(GetProjectTasksQuery query) {
        //미구현
        return List.of();
    }

    @Override
    public TaskResult createTask(Long taskId) {
        //미구현
        return null;
    }
}
