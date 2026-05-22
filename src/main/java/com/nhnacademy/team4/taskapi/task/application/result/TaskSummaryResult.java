package com.nhnacademy.team4.taskapi.task.application.result;


import com.nhnacademy.team4.taskapi.task.domain.Task;

public record TaskSummaryResult(
        Long id,
        Long projectId,
        String title,
        Long writerMemberId,
        Long milestoneId
) {
    public static TaskSummaryResult from(Task task) {
        return new TaskSummaryResult(task.getId(), task.getProject().getId(), task.getTitle(), task.getWriterMemberId(), task.getMilestoneId());
    }
}
