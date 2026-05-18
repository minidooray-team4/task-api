package com.nhnacademy.team4.taskapi.project.application.usecase;

import com.nhnacademy.team4.taskapi.project.application.result.ProjectSummaryResult;

import java.util.List;

public interface GetMyProjectUseCase {
    List<ProjectSummaryResult>  getMyProjects(Long memberId);
}
