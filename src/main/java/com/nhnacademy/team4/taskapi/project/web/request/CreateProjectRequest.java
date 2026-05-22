package com.nhnacademy.team4.taskapi.project.web.request;

import com.nhnacademy.team4.taskapi.project.application.command.CreateProjectCommand;
import jakarta.validation.constraints.NotBlank;

public record CreateProjectRequest(
        @NotBlank String name
)
{

    public CreateProjectCommand toCreateProjectCommand(Long requesterMemberId){
        return new CreateProjectCommand(requesterMemberId,name);
    }
}
