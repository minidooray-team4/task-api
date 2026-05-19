package com.nhnacademy.team4.taskapi.task.web;


import com.nhnacademy.team4.taskapi.task.application.command.CreateTaskCommand;
import com.nhnacademy.team4.taskapi.task.application.command.UpdateTaskCommand;
import com.nhnacademy.team4.taskapi.task.application.result.TaskDetailResult;
import com.nhnacademy.team4.taskapi.task.application.result.TaskResult;
import com.nhnacademy.team4.taskapi.task.application.usecase.CreateTaskUseCase;

import com.nhnacademy.team4.taskapi.task.application.usecase.DeleteTaskUseCase;
import com.nhnacademy.team4.taskapi.task.application.usecase.GetTaskDetailUseCase;
import com.nhnacademy.team4.taskapi.task.application.usecase.UpdateTaskUseCase;
import com.nhnacademy.team4.taskapi.task.web.request.CreateTaskRequest;
import com.nhnacademy.team4.taskapi.task.web.request.UpdateTaskRequest;
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
    private final UpdateTaskUseCase updateTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;

    @PostMapping("/projects/{projectId}/tasks")
    public ResponseEntity<TaskResponse> addTask(
            @PathVariable Long projectId,
            @RequestHeader("X-MEMBER-ID") Long writerMemberId,
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

    @PatchMapping("/tasks/{taskId}")
    public void updateTask(
            @PathVariable Long taskId,
            @RequestHeader("X-MEMBER-ID") Long writerMemberId,
            @RequestBody UpdateTaskRequest request
    ) {
        UpdateTaskCommand command = request.toUpdateTaskCommand(taskId, writerMemberId);
        updateTaskUseCase.updateTask(command);
    }

    @DeleteMapping("/tasks/{taskId}")
    public void deleteTask(
            @PathVariable Long taskId,
            @RequestHeader("X-MEMBER-ID") Long writerMemberId
    ) {
        deleteTaskUseCase.deleteTask(taskId, writerMemberId);
    }



}
