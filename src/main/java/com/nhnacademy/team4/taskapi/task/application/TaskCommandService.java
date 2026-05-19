package com.nhnacademy.team4.taskapi.task.application;

import com.nhnacademy.team4.taskapi.global.exception.BusinessException;

import com.nhnacademy.team4.taskapi.global.exception.ErrorCode;
import com.nhnacademy.team4.taskapi.milestone.domain.MileStone;
import com.nhnacademy.team4.taskapi.milestone.infrastructure.MilestoneRepository;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectRepository;
import com.nhnacademy.team4.taskapi.project.domain.Project;
import com.nhnacademy.team4.taskapi.task.application.command.*;
import com.nhnacademy.team4.taskapi.task.application.result.TaskResult;
import com.nhnacademy.team4.taskapi.task.application.usecase.*;
import com.nhnacademy.team4.taskapi.task.domain.Task;
import com.nhnacademy.team4.taskapi.task.infrastructure.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.nhnacademy.team4.taskapi.global.exception.ErrorCode.PROJECT_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class TaskCommandService implements AssignMilestoneToTaskUseCase, CreateTaskUseCase, RemoveMilestoneFromTaskUseCase, UpdateTaskUseCase,DeleteTaskUseCase{

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final MilestoneRepository milestoneRepository;

    @Override
    public TaskResult assignMilestoneToTask(AssignMilestoneToTaskCommand command) {
        //미구현
        return null;
    }

    @Override
    public TaskResult createTask(CreateTaskCommand command) {

        Project project = projectRepository.findById(command.projectId())
                .orElseThrow(() -> new BusinessException(PROJECT_NOT_FOUND));

        MileStone milestone = null;
        if (command.milestoneId() != null) {
            milestone = milestoneRepository.findById(command.milestoneId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.MILESTONE_NOT_FOUND));
        }

        Task newTask = Task.create(
                command.title(),
                command.content(),
                milestone,
                project,
                command.requesterMemberId()
        );


        Task savedTask = taskRepository.save(newTask);

        return TaskResult.from(savedTask);
    }


    @Override
    public TaskResult updateTask(UpdateTaskCommand command) {
        //미구현
        return null;
    }

    @Override
    public TaskResult removeMilestoneFromTask(Long taskId, Long requesterMemberId) {
        //미구현
        return null;
    }

    @Override
    public void deleteTask(Long taskId, Long requesterMemberId) {
        //미구현
    }
}
