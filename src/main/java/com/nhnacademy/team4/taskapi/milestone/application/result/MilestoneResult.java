package com.nhnacademy.team4.taskapi.milestone.application.result;


import com.nhnacademy.team4.taskapi.milestone.domain.MileStone;

import java.sql.Date;

public record MilestoneResult(
        Long id,
        Long projectId,
        String name,
        Date duDate
) {
    public static MilestoneResult from(MileStone mileStone) {
        return new MilestoneResult(mileStone.getId(),mileStone.getProject().getId(),mileStone.getName(),mileStone.getDueDate());
    }
}
