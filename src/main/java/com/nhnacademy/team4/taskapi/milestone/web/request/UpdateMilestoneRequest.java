package com.nhnacademy.team4.taskapi.milestone.web.request;

import com.nhnacademy.team4.taskapi.milestone.application.command.UpdateMilestoneCommand;
import java.time.LocalDate;

public record UpdateMilestoneRequest(
        String name,
        LocalDate dueDate
) {
    public UpdateMilestoneRequest {
        if (name != null && name.isBlank()) {
            name = null;
        }
    }

    public UpdateMilestoneCommand toCommand(
            Long milestoneId,
            Long requesterMemberId
    ) {
        return new UpdateMilestoneCommand(
                milestoneId,
                requesterMemberId,
                name,
                dueDate
        );
    }
}
