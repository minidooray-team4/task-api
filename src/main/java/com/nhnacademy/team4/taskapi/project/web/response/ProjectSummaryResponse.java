package com.nhnacademy.team4.taskapi.project.web.response;

import com.nhnacademy.team4.taskapi.project.application.result.ProjectSummaryResult;
import com.nhnacademy.team4.taskapi.project.domain.Status;

public record ProjectSummaryResponse(Long id, String name, Status status,Long adminMemberId) {

    public static ProjectSummaryResponse from(ProjectSummaryResult result) {
        return new ProjectSummaryResponse(result.id(),  result.name(), result.status(), result.adminMemberId());
    }
}
