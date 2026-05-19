package com.nhnacademy.team4.taskapi.tags.application.command;

public record DetachTagFromTaskCommand(
        Long taskId,
        Long tagId,
        Long requesterMemberId
) {
}
