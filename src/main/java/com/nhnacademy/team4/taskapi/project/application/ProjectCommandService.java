package com.nhnacademy.team4.taskapi.project.application;

import com.nhnacademy.team4.taskapi.project.application.command.AddProjectMemberCommand;
import com.nhnacademy.team4.taskapi.project.application.command.CreateProjectCommand;
import com.nhnacademy.team4.taskapi.project.application.command.UpdateProjectCommand;

import com.nhnacademy.team4.taskapi.project.application.result.ProjectSummaryResult;
import com.nhnacademy.team4.taskapi.project.application.usecase.AddProjectMemberUseCase;
import com.nhnacademy.team4.taskapi.project.application.usecase.CreateProjectUseCase;
import com.nhnacademy.team4.taskapi.project.application.usecase.UpdateProjectUseCase;
import com.nhnacademy.team4.taskapi.project.domain.Project;

import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectCommandService implements AddProjectMemberUseCase, CreateProjectUseCase, UpdateProjectUseCase {

    private final ProjectRepository projectRepository;

    @Override
    public void addProjectMember(AddProjectMemberCommand command) {
        //미구현

        projectRepository.
    }

    @Override
    public ProjectSummaryResult createProject(CreateProjectCommand command) {

        Project project = Project.create(
                command.name(),
                command.requesterMemberId()
        );

        Project saved = projectRepository.save(project);

        return ProjectSummaryResult.from(saved);

    }

    @Override
    public ProjectSummaryResult updateProject(UpdateProjectCommand command) {
        //미구현
        return null;
    }
}
