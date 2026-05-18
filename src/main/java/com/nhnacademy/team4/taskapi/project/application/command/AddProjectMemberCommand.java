package com.nhnacademy.team4.taskapi.project.application.command;

public record AddProjectMemberCommand(Long projectId,Long targetMemberId,Long requesterMemberId) {
}
