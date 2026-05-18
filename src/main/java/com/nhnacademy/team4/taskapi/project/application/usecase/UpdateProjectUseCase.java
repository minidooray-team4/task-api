package com.nhnacademy.team4.taskapi.project.application.usecase;

import com.nhnacademy.team4.taskapi.project.application.command.UpdateProjectCommand;
import com.nhnacademy.team4.taskapi.project.application.result.ProjectSummaryResult;


public interface UpdateProjectUseCase {
    ProjectSummaryResult updateProject(UpdateProjectCommand command);
}
