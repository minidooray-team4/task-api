package com.nhnacademy.team4.taskapi.tag.web.request;

import com.nhnacademy.team4.taskapi.tag.application.command.CreateTagCommand;

public record CreateTagRequest(
        String name
) {
    public CreateTagCommand toCreateTagCommand(Long projectId,Long requesterMemberId) {
        return new CreateTagCommand(projectId,requesterMemberId,name);
    }
}
