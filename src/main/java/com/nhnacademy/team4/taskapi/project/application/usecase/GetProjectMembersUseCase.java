package com.nhnacademy.team4.taskapi.project.application.usecase;

import com.nhnacademy.team4.taskapi.project.application.result.ProjectMemberResult;

import java.util.List;

public interface GetProjectMembersUseCase {
    List<ProjectMemberResult> getProjectMembers(Long projectId,Long requesterMemberId);
}
