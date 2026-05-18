package com.nhnacademy.team4.taskapi.tags.application.usecase;

import com.nhnacademy.team4.taskapi.tags.application.command.CreateTagCommand;
import com.nhnacademy.team4.taskapi.task.application.result.TagResult;

public interface CreateTagUseCase {
    TagResult createTag(CreateTagCommand command);
}
