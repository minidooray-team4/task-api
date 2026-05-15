package com.nhnacademy.team4.taskapi.task.application.result;

import com.nhnacademy.team4.taskapi.comment.application.result.CommentResult;

import java.util.List;

public record TaskDetailResult(
        Long id,
        Long projectId,
        String title,
        String content,
        Long writerMemberId,
        Long milestoneId,
        List<TagResult> tags,
        List<CommentResult> comments
) {
}