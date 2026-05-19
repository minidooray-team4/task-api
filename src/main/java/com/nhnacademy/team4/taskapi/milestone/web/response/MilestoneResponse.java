package com.nhnacademy.team4.taskapi.milestone.web.response;

import com.nhnacademy.team4.taskapi.milestone.application.result.MilestoneResult;

import java.sql.Date;

public record MilestoneResponse(
        Long id,
        Long projectId,
        String name,
        Date dueDate
) {
    public static MilestoneResponse from(MilestoneResult milestoneResult) {
        return new MilestoneResponse(
                milestoneResult.id(),
                milestoneResult.projectId(),
                milestoneResult.name(),
                milestoneResult.dueDate()
        );
    }
}
