package com.nhnacademy.team4.taskapi.tags.application.usecase;

import com.nhnacademy.team4.taskapi.tags.application.command.UpdateTagCommand;
import com.nhnacademy.team4.taskapi.tags.application.result.TagResult;


public interface UpdateTagUseCase {
    TagResult updateTag(UpdateTagCommand command);
}
