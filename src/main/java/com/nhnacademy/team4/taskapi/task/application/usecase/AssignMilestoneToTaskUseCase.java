package com.nhnacademy.team4.taskapi.task.application.usecase;

import com.nhnacademy.team4.taskapi.task.application.command.AssignMilestoneToTaskCommand;
import com.nhnacademy.team4.taskapi.task.application.result.TaskResult;

public interface AssignMilestoneToTaskUseCase {
    TaskResult assignMilestoneToTask(AssignMilestoneToTaskCommand command);
}
