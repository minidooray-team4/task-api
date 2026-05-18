package com.nhnacademy.team4.taskapi.project.web.request;

import com.nhnacademy.team4.taskapi.project.application.command.CreateProjectCommand;

public record CreateProjectRequest(String name) {


    public CreateProjectCommand toCreateProjectCommand(Long requesterMemberId){
        return new CreateProjectCommand(requesterMemberId,name);
    }
}
