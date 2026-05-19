package com.nhnacademy.team4.taskapi.tag.application.usecase;

import com.nhnacademy.team4.taskapi.tag.application.command.UpdateTagCommand;
import com.nhnacademy.team4.taskapi.tag.application.result.TagResult;

public interface UpdateTagUseCase {
    TagResult updateTag(UpdateTagCommand command);
}
