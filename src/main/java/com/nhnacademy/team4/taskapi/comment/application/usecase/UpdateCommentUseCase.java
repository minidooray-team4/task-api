package com.nhnacademy.team4.taskapi.comment.application.usecase;

import com.nhnacademy.team4.taskapi.comment.application.command.UpdateCommentCommand;

public interface UpdateCommentUseCase {
    void updateComment(UpdateCommentCommand command);
}
