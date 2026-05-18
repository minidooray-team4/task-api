package com.nhnacademy.team4.taskapi.task.application.result;


public record TaskSummaryResult(
        Long id,
        Long projectId,
        String title,
        Long writerMemberId,
        Long milestoneId
) {
}
