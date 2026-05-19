package com.nhnacademy.team4.taskapi.milestone.application.result;


import com.nhnacademy.team4.taskapi.milestone.domain.MileStone;

public record MilestoneResult(
        Long id,
        Long projectId,
        String name
) {
    public static MilestoneResult from(MileStone mileStone) {
        return new MilestoneResult(mileStone.getId(),mileStone.getProject().getId(),mileStone.getName());
    }
}
