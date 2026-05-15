package com.nhnacademy.team4.taskapi.task.application;

import com.nhnacademy.team4.taskapi.global.exception.BusinessException;

import com.nhnacademy.team4.taskapi.project.application.infrastructure.ProjectRepository;
import com.nhnacademy.team4.taskapi.project.domain.Project;
import com.nhnacademy.team4.taskapi.task.application.command.*;
import com.nhnacademy.team4.taskapi.task.application.result.TaskResult;
import com.nhnacademy.team4.taskapi.task.application.usecase.*;
import com.nhnacademy.team4.taskapi.task.domain.Task;
import com.nhnacademy.team4.taskapi.task.infrastructure.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.nhnacademy.team4.taskapi.global.exception.ErrorCode.PROJECT_NOT_FOUND;
import static com.nhnacademy.team4.taskapi.global.exception.ErrorCode.TASK_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TaskCommandService implements AssignMilestoneToTaskUseCase, CreateTaskUseCase, DeleteTaskUseCase, RemoveMilestoneFromTaskUseCase, UpdateTaskUseCase {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    @Override
    public TaskResult assignMilestoneToTask(AssignMilestoneToTaskCommand command) {
        //미구현
        return null;
    }

    @Override
    public TaskResult createTask(CreateTaskCommand command) {

        Project project = projectRepository.findById(command.projectId())
                .orElseThrow(() -> new BusinessException(PROJECT_NOT_FOUND));

        Task newTask = Task.create(command.title(), command.content(), command.milestoneId(), project, command.writerMemberId());


        Task savedTask = taskRepository.save(newTask);

        return TaskResult.from(savedTask);

    }

    @Override
    public void deleteTask(DeleteTaskCommand command) {
        Task task = taskRepository.findById(command.taskId())
                .orElseThrow(() -> new BusinessException(TASK_NOT_FOUND));

        taskRepository.delete(task);

    }

    @Override
    public TaskResult removeMilestoneFromTask(RemoveMilestoneFromTaskCommand command) {
        //미구현
        return null;
    }

    @Override
    public TaskResult updateTask(UpdateTaskCommand command) {
        //미구현
        return null;
    }
}
