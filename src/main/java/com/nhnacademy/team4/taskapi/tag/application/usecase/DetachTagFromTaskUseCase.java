package com.nhnacademy.team4.taskapi.tag.application.usecase;

import com.nhnacademy.team4.taskapi.tag.application.command.DetachTagFromTaskCommand;

public interface DetachTagFromTaskUseCase {
    void detachTagFromTask(DetachTagFromTaskCommand command);
}
