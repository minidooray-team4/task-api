package com.nhnacademy.team4.taskapi.project.application.usecase;

import com.nhnacademy.team4.taskapi.project.application.command.AddProjectMemberCommand;

public interface AddProjectMemberUseCase {
    void addProjectMember(AddProjectMemberCommand command);
}
