package com.nhnacademy.team4.taskapi.task.application.command;



public record CreateTaskCommand(
        Long projectId,
        Long requesterMemberId,
        String title,
        String content,
        Long milestoneId)
{

}
