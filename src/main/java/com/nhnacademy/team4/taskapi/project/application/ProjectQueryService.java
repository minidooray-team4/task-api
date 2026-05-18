package com.nhnacademy.team4.taskapi.project.application;

import com.nhnacademy.team4.taskapi.project.application.result.ProjectDetailResult;
import com.nhnacademy.team4.taskapi.project.application.result.ProjectMemberResult;
import com.nhnacademy.team4.taskapi.project.application.result.ProjectSummaryResult;
import com.nhnacademy.team4.taskapi.project.application.usecase.GetMyProjectUseCase;
import com.nhnacademy.team4.taskapi.project.application.usecase.GetProjectDetailUseCase;
import com.nhnacademy.team4.taskapi.project.application.usecase.GetProjectMembersUseCase;
import com.nhnacademy.team4.taskapi.project.domain.ProjectMembers;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectMemberRepository;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProjectQueryService implements GetMyProjectUseCase, GetProjectDetailUseCase, GetProjectMembersUseCase {

    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;

    @Override
    public List<ProjectSummaryResult> getMyProjects(Long memberId) {

        List<ProjectMembers> myProjects =
                projectMemberRepository.findByMemberId(memberId);

        return myProjects.stream()
                .map(ProjectMembers::getProject)
                .map(ProjectSummaryResult::from)
                .toList();
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
