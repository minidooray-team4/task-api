package com.nhnacademy.team4.taskapi.task.application.usecase;


import com.nhnacademy.team4.taskapi.task.application.result.TaskResult;

public interface GetTaskDetailUseCase {
    TaskResult getTaskDetail(Long taskId);
}