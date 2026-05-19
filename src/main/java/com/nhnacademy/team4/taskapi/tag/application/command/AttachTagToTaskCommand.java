package com.nhnacademy.team4.taskapi.tag.application.command;

public record AttachTagToTaskCommand(
        Long taskId,
        Long tagId,
        Long requesterMemberId
) {
}
