package com.nhnacademy.team4.taskapi.task.application;

import com.nhnacademy.team4.taskapi.global.exception.BusinessException;

import com.nhnacademy.team4.taskapi.global.exception.ErrorCode;
import com.nhnacademy.team4.taskapi.milestone.domain.MileStone;
import com.nhnacademy.team4.taskapi.milestone.infrastructure.MilestoneRepository;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectMemberRepository;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectRepository;
import com.nhnacademy.team4.taskapi.project.domain.Project;
import com.nhnacademy.team4.taskapi.task.application.command.*;
import com.nhnacademy.team4.taskapi.task.application.result.TaskResult;
import com.nhnacademy.team4.taskapi.task.application.usecase.*;
import com.nhnacademy.team4.taskapi.task.domain.Task;
import com.nhnacademy.team4.taskapi.task.infrastructure.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.nhnacademy.team4.taskapi.global.exception.ErrorCode.PROJECT_NOT_FOUND;


@Service
@RequiredArgsConstructor
@Transactional
public class TaskCommandService implements AssignMilestoneToTaskUseCase, CreateTaskUseCase, RemoveMilestoneFromTaskUseCase, UpdateTaskUseCase, DeleteTaskUseCase {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final MilestoneRepository milestoneRepository;
    private final ProjectMemberRepository projectMemberRepository;

    @Override
    public TaskResult assignMilestoneToTask(AssignMilestoneToTaskCommand command) {
        //미구현
        return null;
    }

    @Override
    public void createTask(CreateTaskCommand command) {

        Project project = projectRepository.findById(command.projectId())
                .orElseThrow(() -> new BusinessException(PROJECT_NOT_FOUND));

        // 프로젝트 멤버 검증
        validateProjectMember(command.projectId(), command.requesterMemberId());

        MileStone milestone = null;

        // 마일스톤이 프로젝트내 존재하는지 검증
        if (command.milestoneId() != null) {
            milestone = milestoneRepository.findById(command.milestoneId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.MILESTONE_NOT_FOUND));

            if (!milestone.getProject().getId().equals(project.getId())) {
                throw new BusinessException(ErrorCode.INVALID_MILESTONE_PROJECT);
            }
        }

        Task newTask = Task.create(
                command.title(),
                command.content(),
                milestone,
                project,
                command.requesterMemberId()
        );

        taskRepository.save(newTask);

    }


    @Override
    public void updateTask(UpdateTaskCommand command) {

        Task task = taskRepository.findById(command.taskId())
                .orElseThrow(() -> new BusinessException(ErrorCode.TASK_NOT_FOUND));

        // 프로젝트 멤버 검증
        validateProjectMember(task.getProjectId(), command.requesterMemberId());

        task.update(command.title(), command.content());


    }

    @Override
    public TaskResult removeMilestoneFromTask(Long taskId, Long requesterMemberId) {

        return null;
    }

    @Override
    public void deleteTask(Long taskId, Long requesterMemberId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException(ErrorCode.TASK_NOT_FOUND));

        // 프로젝트 멤버 검증
        validateProjectMember(task.getProjectId(), requesterMemberId);

        taskRepository.deleteById(taskId);
    }

    private void validateProjectMember(Long projectId, Long memberId) {
        boolean isMember = projectMemberRepository
                .existsByProjectIdAndMemberId(projectId, memberId);
        if (!isMember) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }
    }
}
