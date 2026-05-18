package com.nhnacademy.team4.taskapi.task.application.command;

public record AssignMilestoneToTaskCommand(
        Long taskId,
        Long milestoneId,
        Long requesterMemberId
) {
}
