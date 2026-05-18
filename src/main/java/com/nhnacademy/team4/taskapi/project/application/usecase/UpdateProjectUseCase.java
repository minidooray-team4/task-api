package com.nhnacademy.team4.taskapi.project.application.usecase;

import com.nhnacademy.team4.taskapi.project.application.command.UpdateProjectCommand;
import com.nhnacademy.team4.taskapi.project.application.result.ProjectResult;

public interface UpdateProjectUseCase {
    ProjectResult updateProject(UpdateProjectCommand command);
}
