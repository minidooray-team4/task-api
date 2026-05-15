package com.nhnacademy.team4.taskapi.task.application.result;

import com.nhnacademy.team4.taskapi.comment.application.result.CommentResult;
import com.nhnacademy.team4.taskapi.task.domain.Task;

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
    public static TaskDetailResult from(Task task, List<TagResult> tags, List<CommentResult> comments) {
        return new TaskDetailResult(
                task.getId(),
                task.getProject().getId(),
                task.getTitle(),
                task.getContent(),
                task.getWriterMemberId(),
                task.getMilestoneId(),
                tags,
                comments
        );
    }
}