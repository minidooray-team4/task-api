package com.nhnacademy.team4.taskapi.project.application.usecase;

import com.nhnacademy.team4.taskapi.project.application.command.CreateProjectCommand;
import com.nhnacademy.team4.taskapi.project.application.result.ProjectResult;

public interface CreateProjectUseCase {
    ProjectResult createProject(CreateProjectCommand command);
}
