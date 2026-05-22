package com.nhnacademy.team4.taskapi.comment.application.result;

import com.nhnacademy.team4.taskapi.comment.domain.Comment;

public record CommentResult(
        Long id,
        Long taskId,
        Long writerMemberId,
        String content
) {
    public static CommentResult from(Comment comment) {
        return new CommentResult(comment.getId(), comment.getTaskId(), comment.getWriterMemberId(), comment.getContent());
    }
}