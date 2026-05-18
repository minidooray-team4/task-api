package com.nhnacademy.team4.taskapi.tags.web.request;

import com.nhnacademy.team4.taskapi.tags.application.command.CreateTagCommand;

public record CreateTagRequest(
        String name
) {
    public CreateTagCommand toCreateTagCommand(Long projectId,Long requesterMemberId) {
        return new CreateTagCommand(projectId,requesterMemberId,name);
    }
}
