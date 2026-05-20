package com.nhnacademy.team4.taskapi.task.application;

import com.nhnacademy.team4.taskapi.global.exception.BusinessException;

import com.nhnacademy.team4.taskapi.global.exception.ErrorCode;
import com.nhnacademy.team4.taskapi.milestone.domain.Milestone;
import com.nhnacademy.team4.taskapi.milestone.infrastructure.MilestoneRepository;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectMemberRepository;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectRepository;
import com.nhnacademy.team4.taskapi.project.domain.Project;

import com.nhnacademy.team4.taskapi.tags.persistence.TagRepository;
import com.nhnacademy.team4.taskapi.task.application.command.*;


import com.nhnacademy.team4.taskapi.task.domain.Task;
import com.nhnacademy.team4.taskapi.task.infrastructure.TaskRepository;
import com.nhnacademy.team4.taskapi.task.infrastructure.TaskTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.nhnacademy.team4.taskapi.global.exception.ErrorCode.*;


@Service
@RequiredArgsConstructor
@Transactional
public class TaskCommandService {

    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;
    private final TaskTagRepository taskTagRepository;
    private final ProjectRepository projectRepository;
    private final MilestoneRepository milestoneRepository;
    private final ProjectMemberRepository projectMemberRepository;


    public void assignMilestoneToTask(AssignMilestoneToTaskCommand command) {

        Task task = taskRepository.findById(command.taskId())
                .orElseThrow(() -> new BusinessException(TASK_NOT_FOUND));

        // 프로젝트에 존재하는 학생인지 검증
        validateProjectMember(task.getProjectId(), command.requesterMemberId());

        Milestone milestone = milestoneRepository
                .findByProject_IdAndId(
                        task.getProjectId(),
                        command.milestoneId()
                )
                .orElseThrow(() -> new BusinessException(MILESTONE_NOT_FOUND));

        task.assignMilestone(milestone);

    }


    public void createTask(CreateTaskCommand command) {

        Project project = projectRepository.findById(command.projectId())
                .orElseThrow(() -> new BusinessException(PROJECT_NOT_FOUND));


        Milestone milestone = null;

        // 마일스톤이 프로젝트내 존재하는지 검증
        milestone = milestoneRepository
                .findById(command.milestoneId())
                .orElseThrow(() -> new BusinessException(ErrorCode.MILESTONE_NOT_FOUND));

        // 프로젝트 멤버 검증
        validateProjectMember(
                command.projectId(),
                command.requesterMemberId()
        );

        if (command.milestoneId() != null) {
            milestone = milestoneRepository
                    .findByProject_IdAndId(
                            command.projectId(),
                            command.milestoneId()
                    )
                    .orElseThrow(() ->
                            new BusinessException(INVALID_MILESTONE_PROJECT));
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

public void updateTask(UpdateTaskCommand command) {

    Task task = taskRepository.findById(command.taskId())
            .orElseThrow(() -> new BusinessException(ErrorCode.TASK_NOT_FOUND));

    // 프로젝트 멤버 검증
    validateProjectMember(task.getProjectId(), command.requesterMemberId());

    task.update(command.title(), command.content());


}


public void detachMilestoneFromTask(Long taskId, Long requesterMemberId) {

    Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new BusinessException(TASK_NOT_FOUND));

    // 프로젝트에 존재하는 학생인지 검증
    validateProjectMember(task.getProjectId(), requesterMemberId);

    task.detachMilestone();

}


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
