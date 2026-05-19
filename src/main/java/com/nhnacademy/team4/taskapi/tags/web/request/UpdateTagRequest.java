package com.nhnacademy.team4.taskapi.tags.web.request;

import com.nhnacademy.team4.taskapi.tags.application.command.UpdateTagCommand;

public record UpdateTagRequest(String name)
{
    public UpdateTagCommand toUpdateTagCommand(Long tagId, Long requesterMemberId){
        return new UpdateTagCommand(tagId,requesterMemberId,name);
    }
}
