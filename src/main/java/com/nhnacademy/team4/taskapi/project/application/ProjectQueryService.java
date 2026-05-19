package com.nhnacademy.team4.taskapi.project.application;

import com.nhnacademy.team4.taskapi.global.exception.BusinessException;
import com.nhnacademy.team4.taskapi.global.exception.ErrorCode;
import com.nhnacademy.team4.taskapi.milestone.application.result.MilestoneResult;
import com.nhnacademy.team4.taskapi.milestone.infrastructure.MilestoneRepository;
import com.nhnacademy.team4.taskapi.project.application.result.ProjectDetailResult;
import com.nhnacademy.team4.taskapi.project.application.result.ProjectMemberResult;
import com.nhnacademy.team4.taskapi.project.application.result.ProjectSummaryResult;
import com.nhnacademy.team4.taskapi.project.application.usecase.GetMyProjectUseCase;
import com.nhnacademy.team4.taskapi.project.application.usecase.GetProjectDetailUseCase;
import com.nhnacademy.team4.taskapi.project.application.usecase.GetProjectMembersUseCase;
import com.nhnacademy.team4.taskapi.project.domain.Project;
import com.nhnacademy.team4.taskapi.project.domain.ProjectMembers;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectMemberRepository;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectRepository;
import com.nhnacademy.team4.taskapi.tags.application.result.TagResult;
import com.nhnacademy.team4.taskapi.tags.infrastructure.persistence.TagRepository;
import com.nhnacademy.team4.taskapi.task.application.result.TaskSummaryResult;
import com.nhnacademy.team4.taskapi.task.infrastructure.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectQueryService implements GetMyProjectUseCase, GetProjectDetailUseCase, GetProjectMembersUseCase {

    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final TagRepository tagRepository;
    private final TaskRepository taskRepository;
    private final MilestoneRepository milestoneRepository;

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

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROJECT_NOT_FOUND));

        // 프로젝트 멤버인지 검증
        validateProjectAccess(projectId, requesterMemberId);
        
        ProjectSummaryResult summary = ProjectSummaryResult.from(project);

        List<ProjectMemberResult> members = projectMemberRepository.findByProjectId(projectId)
                .stream().map(ProjectMemberResult::from).toList();

        List<TaskSummaryResult> tasks = taskRepository.findByProjectId(projectId)
                .stream().map(TaskSummaryResult::from).toList();

        List<TagResult> tags = tagRepository.findByProjectId(projectId)
                .stream().map(TagResult::from).toList();

        List<MilestoneResult> milestones = milestoneRepository.findByProjectId(projectId)
                .stream().map(MilestoneResult::from).toList();

        return new ProjectDetailResult(
                summary,
                members,
                tasks,
                tags,
                milestones
        );

    }

    @Override
    public List<ProjectMemberResult> getProjectMembers(Long projectId, Long requesterMemberId) {

        // Project 존재유무 및 멤버인지 검증
        validateProjectAccess(projectId, requesterMemberId);

        List<ProjectMembers> members = projectMemberRepository.findByProjectId(projectId);

        return members.stream()
                .map(ProjectMemberResult::from)
                .toList();
    }

    private void validateProjectAccess(Long projectId, Long memberId) {
        boolean isMember = projectMemberRepository.existsByProject_IdAndMemberId(projectId, memberId);

        if (!isMember) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }
    }
}
