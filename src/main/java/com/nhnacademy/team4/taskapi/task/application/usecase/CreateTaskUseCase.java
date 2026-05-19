package com.nhnacademy.team4.taskapi.task.application.usecase;

import com.nhnacademy.team4.taskapi.task.application.command.CreateTaskCommand;

public interface CreateTaskUseCase {
    void createTask(CreateTaskCommand command);
}