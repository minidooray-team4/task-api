package com.nhnacademy.team4.taskapi.milestone.web.response;

import com.nhnacademy.team4.taskapi.milestone.application.result.MilestoneResult;

import java.time.LocalDate;

public record MilestoneResponse(
        Long id,
        Long projectId,
        String name,
        LocalDate dueDate
) {
    public static MilestoneResponse from(MilestoneResult milestoneResult) {
        if(milestoneResult == null) {
            return null;
        }
        return new MilestoneResponse(
                milestoneResult.id(),
                milestoneResult.projectId(),
                milestoneResult.name(),
                milestoneResult.dueDate()
        );
    }
}
