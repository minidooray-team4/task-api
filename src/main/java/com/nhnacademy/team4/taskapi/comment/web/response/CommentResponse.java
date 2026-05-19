package com.nhnacademy.team4.taskapi.comment.web.response;



public record CommentResponse(
        Long id,
        Long taskId,
        Long writerMemberId,
        String content
) {
    public static CommentResponse from(CommentResponse commentResponse) {
        return new CommentResponse(
                commentResponse.id,
                commentResponse.taskId,
                commentResponse.writerMemberId,
                commentResponse.content
        );
    }
}
