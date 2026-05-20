package com.nhnacademy.team4.taskapi.project.web.response;


import com.nhnacademy.team4.taskapi.project.application.result.CreateProjectResult;

public record CreateProjectResponse(Long id) {

    public static CreateProjectResponse from(CreateProjectResult result) {
        return new CreateProjectResponse(result.id());
    }
}
