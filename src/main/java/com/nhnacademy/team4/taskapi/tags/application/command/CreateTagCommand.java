package com.nhnacademy.team4.taskapi.tags.application.command;

public record CreateTagCommand(
        Long projectId,
        Long requesterMemberId,
        String name
) {
}
