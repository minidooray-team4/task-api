package com.nhnacademy.team4.taskapi.tags.application.command;

public record UpdateTagCommand (
        Long tagId,
        Long requesterMemberId,
        String name
){
}
