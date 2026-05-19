package com.nhnacademy.team4.taskapi.tag.application.usecase;

import com.nhnacademy.team4.taskapi.tag.application.command.CreateTagCommand;
import com.nhnacademy.team4.taskapi.tag.application.result.TagResult;

public interface CreateTagUseCase {
    TagResult createTag(CreateTagCommand command);
}
