package com.nhnacademy.team4.taskapi.project.application.command;

public record AddProjectMemberCommand(Long projectId,Long targetMemberId,Long requesterMemberId) {
    public static AddProjectMemberCommand create(Long projectId, Long targetMemberId, Long requesterMemberId) {
        return new AddProjectMemberCommand(projectId, targetMemberId, requesterMemberId);
    }
}
