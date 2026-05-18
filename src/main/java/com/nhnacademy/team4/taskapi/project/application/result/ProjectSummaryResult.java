package com.nhnacademy.team4.taskapi.project.application.result;

import com.nhnacademy.team4.taskapi.project.domain.Project;
import com.nhnacademy.team4.taskapi.project.domain.Status;

public record ProjectSummaryResult(Long id, String name, Status status, Long adminMemberId) {
    public static ProjectSummaryResult from(Project saved) {
        return new ProjectSummaryResult(saved.getId(), saved.getName(), saved.getStatus(), saved.getAdminMemberId());
    }
}
