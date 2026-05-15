package com.nhnacademy.team4.taskapi.task.web;


import com.nhnacademy.team4.taskapi.task.application.command.CreateTaskCommand;
import com.nhnacademy.team4.taskapi.task.application.result.TaskDetailResult;
import com.nhnacademy.team4.taskapi.task.application.result.TaskResult;
import com.nhnacademy.team4.taskapi.task.application.usecase.CreateTaskUseCase;

import com.nhnacademy.team4.taskapi.task.application.usecase.GetTaskDetailUseCase;
import com.nhnacademy.team4.taskapi.task.web.request.CreateTaskRequest;
import com.nhnacademy.team4.taskapi.task.web.response.TaskDetailResponse;
import com.nhnacademy.team4.taskapi.task.web.response.TaskResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final GetTaskDetailUseCase getTaskDetailUseCase;

    @PostMapping("/projects/{projectId}/tasks")
    public ResponseEntity<TaskResponse> addTask(
            @PathVariable Long projectId,
            @RequestHeader("X-USER-ID") Long writerMemberId,
            @RequestBody CreateTaskRequest request
    ) {
        CreateTaskCommand command = request.toCreateTaskCommand(projectId, writerMemberId);

        TaskResult taskResult = createTaskUseCase.createTask(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(TaskResponse.from(taskResult));
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<TaskDetailResponse> getTaskDetails(
            @PathVariable Long taskId

    ) {
        TaskDetailResult taskDetail = getTaskDetailUseCase.getTaskDetail(taskId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(TaskDetailResponse.from(taskDetail));

    }


}
