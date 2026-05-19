package com.nhnacademy.team4.taskapi.comment.application.usecase;

import com.nhnacademy.team4.taskapi.comment.application.command.CreateCommentCommand;

public interface CreateCommentUseCase {
    void createComment(CreateCommentCommand command);
}
