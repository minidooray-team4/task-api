package com.nhnacademy.team4.taskapi.project.application;

import com.nhnacademy.team4.taskapi.global.exception.BusinessException;
import com.nhnacademy.team4.taskapi.global.exception.ErrorCode;
import com.nhnacademy.team4.taskapi.project.application.command.AddProjectMemberCommand;
import com.nhnacademy.team4.taskapi.project.application.command.CreateProjectCommand;
import com.nhnacademy.team4.taskapi.project.application.command.UpdateProjectCommand;

import com.nhnacademy.team4.taskapi.project.application.result.ProjectSummaryResult;
import com.nhnacademy.team4.taskapi.project.application.usecase.AddProjectMemberUseCase;
import com.nhnacademy.team4.taskapi.project.application.usecase.CreateProjectUseCase;
import com.nhnacademy.team4.taskapi.project.application.usecase.UpdateProjectUseCase;
import com.nhnacademy.team4.taskapi.project.domain.Project;

import com.nhnacademy.team4.taskapi.project.domain.ProjectMembers;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectMemberRepository;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectCommandService implements AddProjectMemberUseCase, CreateProjectUseCase, UpdateProjectUseCase {

    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;

    @Override
    public void addProjectMember(AddProjectMemberCommand command) {

        Project project = projectRepository.findById(command.projectId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PROJECT_NOT_FOUND));

        // 관리자 권한 확인
        validateProjectAdmin(project, command.requesterMemberId());

        // 멤버 존재유무 판별
        validateProjectMemberNotExist(project.getId(), command.requesterMemberId());

        ProjectMembers projectMembers = ProjectMembers.create(project, command.targetMemberId());

        projectMemberRepository.save(projectMembers);
    }

    @Override
    public ProjectSummaryResult createProject(CreateProjectCommand command) {

        Project project = Project.create(
                command.name(),
                command.requesterMemberId()
        );

        Project saved = projectRepository.save(project);

        // 관리자도 프로젝트 생성시 멤버에 추가
        ProjectMembers adminMember =
                ProjectMembers.create(saved, command.requesterMemberId());

        projectMemberRepository.save(adminMember);

        return ProjectSummaryResult.from(saved);

    }

    @Override
    public ProjectSummaryResult updateProject(UpdateProjectCommand command) {

        Project project = projectRepository.findById(command.projectId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PROJECT_NOT_FOUND));

        // 관리자 권한 확인
        validateProjectAdmin(project, command.requesterMemberId());

        project.update(command.name(), command.status());

        return ProjectSummaryResult.from(project);
    }

    private void validateProjectAdmin(Project project, Long requesterMemberId) {
        if (!project.getAdminMemberId().equals(requesterMemberId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }
    }

    private void validateProjectMemberNotExist(Long projectId, Long requesterMemberId) {
        if (projectMemberRepository.existsByProjectIdAndMemberId(projectId, requesterMemberId)) {
            throw new BusinessException(ErrorCode.PROJECT_MEMBER_ALREADY_EXISTS);
        }
    }


}
