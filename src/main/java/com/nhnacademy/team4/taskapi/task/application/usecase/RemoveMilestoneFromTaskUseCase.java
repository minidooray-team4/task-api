package com.nhnacademy.team4.taskapi.task.application.usecase;

import com.nhnacademy.team4.taskapi.task.application.command.RemoveMilestoneFromTaskCommand;
import com.nhnacademy.team4.taskapi.task.application.result.TaskResult;

public interface RemoveMilestoneFromTaskUseCase {
    TaskResult removeMilestoneFromTask(RemoveMilestoneFromTaskCommand command);
}
