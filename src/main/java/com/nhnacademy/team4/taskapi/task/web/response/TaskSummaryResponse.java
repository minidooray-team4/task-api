package com.nhnacademy.team4.taskapi.task.web.response;

public record TaskSummaryResponse(
        Long id,
        Long projectId,
        String title,
        Long writerMemberId,
        Long milestoneId

) {
}
