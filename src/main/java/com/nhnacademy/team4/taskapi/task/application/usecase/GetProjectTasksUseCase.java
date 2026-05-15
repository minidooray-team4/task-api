package com.nhnacademy.team4.taskapi.task.application.usecase;

import com.nhnacademy.team4.taskapi.task.application.command.GetProjectTasksQuery;
import com.nhnacademy.team4.taskapi.task.application.result.TaskSummaryResult;

import java.util.List;

public interface GetProjectTasksUseCase {
    List<TaskSummaryResult> getProjectTasks(GetProjectTasksQuery query);
}
