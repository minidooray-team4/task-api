package com.nhnacademy.team4.taskapi.tags.application.command;

public record AttachTagToTaskCommand(
        Long taskId,
        Long tagId,
        Long requesterMemberId
) {
}
