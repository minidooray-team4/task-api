package com.nhnacademy.team4.taskapi.tags.application.usecase;

import com.nhnacademy.team4.taskapi.tags.application.command.DetachTagFromTaskCommand;

public interface DetachTagFromTaskUseCase {
    void detachTagFromTask(DetachTagFromTaskCommand command);
}
