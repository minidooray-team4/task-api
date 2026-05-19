package com.nhnacademy.team4.taskapi.milestone.application.usecase;

import com.nhnacademy.team4.taskapi.milestone.application.command.UpdateMilestoneCommand;

public interface UpdateMilestoneUseCase {
    void updateMilestone(UpdateMilestoneCommand command);
}
