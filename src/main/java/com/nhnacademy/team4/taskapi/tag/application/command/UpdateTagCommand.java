package com.nhnacademy.team4.taskapi.tag.application.command;

public record UpdateTagCommand (
        Long tagId,
        Long requesterMemberId,
        String name
){
}
