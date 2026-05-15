package com.nhnacademy.team4.taskapi.task.web;


import com.nhnacademy.team4.taskapi.task.application.command.CreateTaskCommand;
import com.nhnacademy.team4.taskapi.task.application.result.TaskResult;
import com.nhnacademy.team4.taskapi.task.application.usecase.CreateTaskUseCase;

import com.nhnacademy.team4.taskapi.task.web.request.CreateTaskRequest;
import com.nhnacademy.team4.taskapi.task.web.response.TaskResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class TaskController {
    private final CreateTaskUseCase createTaskUseCase;

    @PostMapping("/api/projects/{projectId}/tasks")
    public TaskResponse addTask(
            @PathVariable Long projectId,
            @RequestHeader("X-USER-ID") Long writerMemberId,
            @RequestBody CreateTaskRequest request
    ) {

        CreateTaskCommand command = request.toCreateTaskCommand(
                projectId, writerMemberId
        );

        TaskResult taskResult = createTaskUseCase.createTask(command);

        return TaskResponse.from(taskResult);


    }


}
