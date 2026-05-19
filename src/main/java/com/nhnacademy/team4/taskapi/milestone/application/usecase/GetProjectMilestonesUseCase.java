package com.nhnacademy.team4.taskapi.milestone.application.usecase;

import com.nhnacademy.team4.taskapi.milestone.application.result.MilestoneResult;
import java.util.List;

public interface GetProjectMilestonesUseCase {
    List<MilestoneResult> getProjectMilestones(Long projectId, Long requesterMemberId);
}
