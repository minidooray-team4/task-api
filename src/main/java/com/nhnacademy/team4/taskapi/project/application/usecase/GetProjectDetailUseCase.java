package com.nhnacademy.team4.taskapi.project.application.usecase;

import com.nhnacademy.team4.taskapi.project.application.result.ProjectDetailResult;

public interface GetProjectDetailUseCase {
    ProjectDetailResult getProjectDetail(Long projectId,Long requesterMemberId);
}
