package com.nhnacademy.team4.taskapi.comment.web.request;

import com.nhnacademy.team4.taskapi.comment.application.command.CreateCommentCommand;
import jakarta.validation.constraints.NotBlank;

public record CreateCommentRequest(
        @NotBlank
        String content
) {
    public CreateCommentCommand toCommand(
            Long taskId,
            Long requesterMemberId
    ) {
        return new CreateCommentCommand(
                taskId,
                requesterMemberId,
                content
        );
    }
}
