package com.nhnacademy.team4.taskapi.milestone.web.request;

import com.nhnacademy.team4.taskapi.milestone.application.command.CreateMilestoneCommand;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public record CreateMilestoneRequest(

        @NotBlank
        String name,

        LocalDate dueDate
) {
    public CreateMilestoneCommand toCommand(
            Long projectId,
            Long requesterMemberId
    ) {
        return new CreateMilestoneCommand(
                projectId,
                requesterMemberId,
                name,
                dueDate
        );
    }
}
