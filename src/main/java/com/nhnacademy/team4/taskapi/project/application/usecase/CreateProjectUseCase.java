package com.nhnacademy.team4.taskapi.project.application.usecase;

import com.nhnacademy.team4.taskapi.project.application.command.CreateProjectCommand;
import com.nhnacademy.team4.taskapi.project.application.result.ProjectSummaryResult;

public interface CreateProjectUseCase {
    ProjectSummaryResult createProject(CreateProjectCommand command);
}
