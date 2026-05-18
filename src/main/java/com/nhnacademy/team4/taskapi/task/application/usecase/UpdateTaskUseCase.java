package com.nhnacademy.team4.taskapi.task.application.usecase;

import com.nhnacademy.team4.taskapi.task.application.command.UpdateTaskCommand;
import com.nhnacademy.team4.taskapi.task.application.result.TaskResult;

public interface UpdateTaskUseCase {
    TaskResult updateTask(UpdateTaskCommand command);
}
