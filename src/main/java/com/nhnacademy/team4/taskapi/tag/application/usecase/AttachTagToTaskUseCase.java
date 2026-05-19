package com.nhnacademy.team4.taskapi.tag.application.usecase;

import com.nhnacademy.team4.taskapi.tag.application.command.AttachTagToTaskCommand;

public interface AttachTagToTaskUseCase {
    void attachTagToTask(AttachTagToTaskCommand command);
}
