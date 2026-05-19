package com.nhnacademy.team4.taskapi.comment.application.command;

public record CreateCommentCommand(
        Long taskId,
        Long requesterMemberId,
        String content
) {
}
