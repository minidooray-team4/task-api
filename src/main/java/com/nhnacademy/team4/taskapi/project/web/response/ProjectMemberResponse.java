package com.nhnacademy.team4.taskapi.project.web.response;

import com.nhnacademy.team4.taskapi.project.application.result.ProjectMemberResult;

public record ProjectMemberResponse(Long id, Long projectId, Long memberId) {
    public static ProjectMemberResponse from(ProjectMemberResult projectMemberResult) {
        return new ProjectMemberResponse(projectMemberResult.id(), projectMemberResult.projectId(), projectMemberResult.memberId());
    }
}
