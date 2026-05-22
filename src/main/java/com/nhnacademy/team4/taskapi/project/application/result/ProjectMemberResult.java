package com.nhnacademy.team4.taskapi.project.application.result;

import com.nhnacademy.team4.taskapi.project.domain.ProjectMembers;

public record ProjectMemberResult(Long id, Long projectId, Long memberId) {
    public static ProjectMemberResult from(ProjectMembers members) {
        return new ProjectMemberResult(members.getId(),members.getProject().getId(),members.getMemberId());
    }
}
