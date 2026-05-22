package com.nhnacademy.team4.taskapi.milestone.application.command;

import java.time.LocalDate;

public record CreateMilestoneCommand(
        Long projectId,
        Long requesterMemberId,
        String name,
        LocalDate dueDate
) {
}
