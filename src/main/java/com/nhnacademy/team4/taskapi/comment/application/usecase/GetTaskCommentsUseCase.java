package com.nhnacademy.team4.taskapi.comment.application.usecase;

import com.nhnacademy.team4.taskapi.comment.application.result.CommentResult;
import java.util.List;

public interface GetTaskCommentsUseCase {
    List<CommentResult> getTaskComments(Long taskId, Long requesterMemberId);
}
