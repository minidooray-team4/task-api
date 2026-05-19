package com.nhnacademy.team4.taskapi.comment.application.usecase;

public interface DeleteCommentUseCase {
    void deleteComment(Long commentId, Long requesterMemberId);
}
