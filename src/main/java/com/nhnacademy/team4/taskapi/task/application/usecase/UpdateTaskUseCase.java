package com.nhnacademy.team4.taskapi.task.application.usecase;

import com.nhnacademy.team4.taskapi.task.application.command.UpdateTaskCommand;

public interface UpdateTaskUseCase {
    void updateTask(UpdateTaskCommand command);
}
