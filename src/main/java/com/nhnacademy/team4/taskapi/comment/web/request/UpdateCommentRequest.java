package com.nhnacademy.team4.taskapi.comment.web.request;

import com.nhnacademy.team4.taskapi.comment.application.command.UpdateCommentCommand;
import jakarta.validation.constraints.NotBlank;

public record UpdateCommentRequest(
        @NotBlank
        String content
) {
    public UpdateCommentCommand toCommand(
            Long commentId,
            Long requesterMemberId
    ) {
        return new UpdateCommentCommand(
                commentId,
                requesterMemberId,
                content
        );
    }
}
