package com.nhnacademy.team4.taskapi.task.web.response;

import com.nhnacademy.team4.taskapi.task.application.result.TaskSummaryResult;

public record TaskSummaryResponse(
        Long id,
        Long projectId,
        String title,
        Long writerMemberId,
        Long milestoneId

) {
    public static TaskSummaryResponse from(TaskSummaryResult taskSummaryResult) {
        return new TaskSummaryResponse(
                taskSummaryResult.id(),
                taskSummaryResult.projectId(),
                taskSummaryResult.title(),
                taskSummaryResult.writerMemberId(),
                taskSummaryResult.milestoneId()
        );
    }
}
