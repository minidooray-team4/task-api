package com.nhnacademy.team4.taskapi.task.application.usecase;

import com.nhnacademy.team4.taskapi.task.application.command.CreateTaskCommand;
import com.nhnacademy.team4.taskapi.task.application.command.DeleteTaskCommand;
import com.nhnacademy.team4.taskapi.task.application.result.TaskResult;

public interface DeleteTaskUseCase {
    void deleteTask(DeleteTaskCommand command);
}