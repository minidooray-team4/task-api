package com.nhnacademy.team4.taskapi.task.application.command;

public record AssignMilestoneToTaskCommand(
        Long taskId,
        Long milestoneId,
        Long requesterMemberId
) {
    public static AssignMilestoneToTaskCommand toCommand(Long taskId, Long milestoneId, Long writerMemberId) {
        return new AssignMilestoneToTaskCommand(taskId, milestoneId, writerMemberId);
    }
}
