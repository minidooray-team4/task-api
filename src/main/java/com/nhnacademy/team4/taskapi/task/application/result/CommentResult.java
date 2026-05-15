package com.nhnacademy.team4.taskapi.task.application.result;

public record CommentResult(
        Long id,
        Long writerMemberId,
        String content
) {
}