package com.nhnacademy.team4.taskapi.project.application.usecase;

import com.nhnacademy.team4.taskapi.project.application.command.UpdateProjectCommand;


public interface UpdateProjectUseCase {
    void updateProject(UpdateProjectCommand command);
}
