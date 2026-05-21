package com.nhnacademy.team4.taskapi.project.web.request;

import com.nhnacademy.team4.taskapi.project.application.command.UpdateProjectCommand;
import com.nhnacademy.team4.taskapi.project.domain.Status;

public record UpdateProjectRequest(
        String name,
        Status status
) {

    public UpdateProjectCommand toUpdateProjectCommand(Long projectId, Long requesterMemberId) {
        return new UpdateProjectCommand(projectId,requesterMemberId,name, status);
    }
}
