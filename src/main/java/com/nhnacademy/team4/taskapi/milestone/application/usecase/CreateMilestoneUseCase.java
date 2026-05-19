package com.nhnacademy.team4.taskapi.milestone.application.usecase;

import com.nhnacademy.team4.taskapi.milestone.application.command.CreateMilestoneCommand;

public interface CreateMilestoneUseCase {
    void createMilestone(CreateMilestoneCommand command);
}
