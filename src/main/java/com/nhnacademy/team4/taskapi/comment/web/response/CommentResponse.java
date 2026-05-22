package com.nhnacademy.team4.taskapi.comment.web.response;

import com.nhnacademy.team4.taskapi.comment.application.result.CommentResult;

public record CommentResponse(
        Long id,
        Long taskId,
        Long writerMemberId,
        String content
) {
    public static CommentResponse from(CommentResult result) {
        return new CommentResponse(
                result.id(),
                result.taskId(),
                result.writerMemberId(),
                result.content()
        );
    }
}
