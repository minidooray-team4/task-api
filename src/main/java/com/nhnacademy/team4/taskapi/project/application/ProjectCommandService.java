package com.nhnacademy.team4.taskapi.project.application;

import com.nhnacademy.team4.taskapi.project.application.command.AddProjectMemberCommand;
import com.nhnacademy.team4.taskapi.project.application.command.CreateProjectCommand;
import com.nhnacademy.team4.taskapi.project.application.command.UpdateProjectCommand;
import com.nhnacademy.team4.taskapi.project.application.result.ProjectResult;
import com.nhnacademy.team4.taskapi.project.application.usecase.AddProjectMemberUseCase;
import com.nhnacademy.team4.taskapi.project.application.usecase.CreateProjectUseCase;
import com.nhnacademy.team4.taskapi.project.application.usecase.UpdateProjectUseCase;

public class ProjectCommandService implements AddProjectMemberUseCase, CreateProjectUseCase, UpdateProjectUseCase {

    @Override
    public void addProjectMember(AddProjectMemberCommand command) {

    }

    @Override
    public ProjectResult createProject(CreateProjectCommand command) {
        //미구현
        return null;
    }

    @Override
    public ProjectResult updateProject(UpdateProjectCommand command) {
        //미구현
        return null;
    }
}
