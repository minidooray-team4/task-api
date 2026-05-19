package com.nhnacademy.team4.taskapi.tags.application.usecase;

import com.nhnacademy.team4.taskapi.tags.application.command.AttachTagToTaskCommand;

public interface AttachTagToTaskUseCase {
    void attachTagToTask(AttachTagToTaskCommand command);
}
