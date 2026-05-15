package com.nhnacademy.team4.taskapi.task.application.usecase;


import com.nhnacademy.team4.taskapi.task.application.result.TaskDetailResult;


public interface GetTaskDetailUseCase {
    TaskDetailResult getTaskDetail(Long taskId);
}