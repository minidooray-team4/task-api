package com.nhnacademy.team4.taskapi.task.application;

import com.nhnacademy.team4.taskapi.task.application.command.*;
import com.nhnacademy.team4.taskapi.task.application.result.TaskResult;
import com.nhnacademy.team4.taskapi.task.application.usecase.*;

public class TaskCommandService implements AssignMilestoneToTaskUseCase, CreateTaskUseCase, DeleteTaskUseCase, RemoveMilestoneFromTaskUseCase, UpdateTaskUseCase {
    @Override
    public TaskResult assignMilestoneToTask(AssignMilestoneToTaskCommand command) {
        //미구현
        return null;
    }

    @Override
    public TaskResult createTask(CreateTaskCommand command) {
        //미구현
        return null;
    }

    @Override
    public void deleteTask(DeleteTaskCommand command) {
        //미구현
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
