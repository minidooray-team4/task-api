package com.nhnacademy.team4.taskapi.tag.application.command;

public record CreateTagCommand(
        Long projectId,
        Long requesterMemberId,
        String name
) {
}
