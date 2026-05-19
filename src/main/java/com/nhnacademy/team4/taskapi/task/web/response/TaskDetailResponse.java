package com.nhnacademy.team4.taskapi.task.web.response;

import com.nhnacademy.team4.taskapi.comment.application.result.CommentResult;

import com.nhnacademy.team4.taskapi.comment.web.response.CommentResponse;
import com.nhnacademy.team4.taskapi.tags.application.result.TagResult;
import com.nhnacademy.team4.taskapi.tags.web.response.TagResponse;
import com.nhnacademy.team4.taskapi.task.application.result.TaskDetailResult;

import java.util.List;

public record TaskDetailResponse(
        Long id,
        Long projectId,
        String title,
        String content,
        Long writerMemberId,
        Long milestoneId,
        List<TagResponse> tags,
        List<CommentResponse> comments

) {
    public static TaskDetailResponse from(TaskDetailResult taskDetail) {
       return null;
    }
}
