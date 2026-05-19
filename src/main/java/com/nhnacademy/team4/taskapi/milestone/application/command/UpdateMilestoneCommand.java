package com.nhnacademy.team4.taskapi.milestone.application.command;

import java.time.LocalDate;

public record UpdateMilestoneCommand(
        Long milestoneId,
        Long requesterMemberId,
        String name,
        LocalDate dueDate
) {
}
