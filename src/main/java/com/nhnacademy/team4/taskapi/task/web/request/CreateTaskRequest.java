package com.nhnacademy.team4.taskapi.task.web.request;


import com.nhnacademy.team4.taskapi.task.application.command.CreateTaskCommand;
import jakarta.validation.constraints.NotBlank;

public record CreateTaskRequest(
        @NotBlank String title,
        String content,
        Long milestoneId)
{
    public CreateTaskCommand toCreateTaskCommand(Long projectId, Long writerMemberId) {
        return new CreateTaskCommand(projectId,writerMemberId,title,content,milestoneId);
    }

}
