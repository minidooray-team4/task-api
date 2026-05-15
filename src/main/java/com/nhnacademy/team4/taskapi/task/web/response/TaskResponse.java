package com.nhnacademy.team4.taskapi.task.web.response;

import com.nhnacademy.team4.taskapi.task.application.result.TaskResult;

public record TaskResponse(
        Long id,
        Long projectId,
        String title,
        String content,
        Long writerMemberId,
        Long milestoneId
) {
    public static TaskResponse from(TaskResult taskResult) {
        return new TaskResponse(
                taskResult.id(),
                taskResult.projectId(),
                taskResult.title(),
                taskResult.content(),
                taskResult.writerMemberId(),
                taskResult.milestoneId()
        );
    }
}
