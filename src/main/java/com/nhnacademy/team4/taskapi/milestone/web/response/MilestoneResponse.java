package com.nhnacademy.team4.taskapi.milestone.web.response;

import com.nhnacademy.team4.taskapi.milestone.application.result.MilestoneResult;

public record MilestoneResponse(
        Long id,
        Long projectId,
        String name
) {
    public static MilestoneResponse from(MilestoneResult milestoneResult) {
        return new MilestoneResponse(
                milestoneResult.id(),
                milestoneResult.projectId(),
                milestoneResult.name()
        );
    }
}
