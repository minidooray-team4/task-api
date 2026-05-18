package com.nhnacademy.team4.taskapi.comment.application.result;

public record CommentResult(
        Long id,
        Long taskId,
        Long writerMemberId,
        String content
) {
}