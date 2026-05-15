package com.nhnacademy.team4.taskapi.task.application.command;

public record GetProjectTasksQuery(
        Long projectId,
        Long memberId,
        Long milestoneId,
        Long tagId
) {
}