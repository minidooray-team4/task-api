package com.nhnacademy.team4.taskapi.project.application.command;

import com.nhnacademy.team4.taskapi.project.domain.Status;

public record UpdateProjectCommand(Long projectId, Long requesterMemberId, String name, Status status) {
}
