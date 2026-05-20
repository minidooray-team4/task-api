package com.nhnacademy.team4.taskapi.task.web.response;



import com.nhnacademy.team4.taskapi.comment.web.response.CommentResponse;
import com.nhnacademy.team4.taskapi.milestone.web.response.MilestoneResponse;
import com.nhnacademy.team4.taskapi.tags.web.response.TagResponse;
import com.nhnacademy.team4.taskapi.task.application.result.TaskDetailResult;

import java.util.List;

public record TaskDetailResponse(
        Long id,
        Long projectId,
        String title,
        String content,
        Long writerMemberId,
        MilestoneResponse milestoneResponse,
        List<TagResponse> tags,
        List<CommentResponse> comments

) {
    public static TaskDetailResponse from(TaskDetailResult taskDetail) {
        return new TaskDetailResponse(
                taskDetail.id(),
                taskDetail.projectId(),
                taskDetail.title(),
                taskDetail.content(),
                taskDetail.writerMemberId(),
                MilestoneResponse.from(taskDetail.milestoneResult()),
                taskDetail.tags()
                        .stream()
                        .map(TagResponse::from)
                        .toList(),
                taskDetail.comments().stream()
                        .map(CommentResponse::from)
                        .toList()
        );
    }
}
