package com.nhnacademy.team4.taskapi.project.application;

import com.nhnacademy.team4.taskapi.project.application.result.ProjectDetailResult;
import com.nhnacademy.team4.taskapi.project.application.result.ProjectMemberResult;
import com.nhnacademy.team4.taskapi.project.application.result.ProjectSummaryResult;
import com.nhnacademy.team4.taskapi.project.application.usecase.GetMyProjectUseCase;
import com.nhnacademy.team4.taskapi.project.application.usecase.GetProjectDetailUseCase;
import com.nhnacademy.team4.taskapi.project.application.usecase.GetProjectMembersUseCase;

import java.util.List;

public class ProjectQueryService implements GetMyProjectUseCase, GetProjectDetailUseCase, GetProjectMembersUseCase {
    @Override
    public List<ProjectSummaryResult> getMyProjects(Long memberId) {
        //미구현
        return List.of();
    }

    @Override
    public ProjectDetailResult getProjectDetail(Long projectId, Long requesterMemberId) {
        //미구현
        return null;
    }

    @Override
    public List<ProjectMemberResult> getProjectMembers(Long projectId, Long requesterMemberId) {
        //미구현
        return List.of();
    }
}
