package com.nhnacademy.team4.taskapi.task.application.usecase;

import com.nhnacademy.team4.taskapi.task.application.result.TaskDetailResult;

public interface GetProjectTaskUseCase {
    TaskDetailResult  getProjectTask(GetProjectTaskUseCase command);
}