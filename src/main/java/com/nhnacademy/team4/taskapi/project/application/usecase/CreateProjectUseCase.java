package com.nhnacademy.team4.taskapi.project.application.usecase;

import com.nhnacademy.team4.taskapi.project.application.command.CreateProjectCommand;

public interface CreateProjectUseCase {
    Long createProject(CreateProjectCommand command);
}
