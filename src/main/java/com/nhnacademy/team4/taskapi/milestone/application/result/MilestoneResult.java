package com.nhnacademy.team4.taskapi.milestone.application.result;


import com.nhnacademy.team4.taskapi.milestone.domain.Milestone;

import java.time.LocalDate;

public record MilestoneResult(
        Long id,
        Long projectId,
        String name,
        LocalDate dueDate
) {
    public static MilestoneResult from(Milestone mileStone) {
        if (mileStone == null) {
            return null;
        }
        return new MilestoneResult(mileStone.getId(), mileStone.getProject().getId(), mileStone.getName(), mileStone.getDueDate());
    }
}
