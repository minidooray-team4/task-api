package com.nhnacademy.team4.taskapi.task.web.response;

import com.nhnacademy.team4.taskapi.comment.application.result.CommentResult;
import com.nhnacademy.team4.taskapi.task.application.result.TagResult;
import com.nhnacademy.team4.taskapi.task.application.result.TaskDetailResult;

import java.util.List;

public record TaskDetailResponse(
        Long id,
        Long projectId,
        String title,
        String content,
        Long writerMemberId,
        Long milestoneId,
        List<TagResult> tags,
        List<CommentResult> comments

) {
    public static TaskDetailResponse from(TaskDetailResult taskDetail) {
        return new TaskDetailResponse(
                taskDetail.id(),
                taskDetail.projectId(),
                taskDetail.title(),
                taskDetail.content(),
                taskDetail.writerMemberId(),
                taskDetail.milestoneId(),
                taskDetail.tags(),
                taskDetail.comments()
        );
    }
}
