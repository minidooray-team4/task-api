package com.nhnacademy.team4.taskapi.comment.application.command;

public record UpdateCommentCommand(
        Long commentId,
        Long requesterMemberId,
        String content
) {
}
