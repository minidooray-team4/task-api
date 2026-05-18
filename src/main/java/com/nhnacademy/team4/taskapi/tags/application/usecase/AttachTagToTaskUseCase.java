package com.nhnacademy.team4.taskapi.tags.application.usecase;

import com.nhnacademy.team4.taskapi.tags.application.command.AttachTagToTaskCommand;
import com.nhnacademy.team4.taskapi.task.application.result.TaskResult;

public interface AttachTagToTaskUseCase {
    void attachTagToTask(AttachTagToTaskCommand command);
}
