package com.nhnacademy.team4.taskapi.task.web;


import com.nhnacademy.team4.taskapi.task.application.TaskCommandService;
import com.nhnacademy.team4.taskapi.task.application.TaskQueryService;
import com.nhnacademy.team4.taskapi.task.application.command.AssignMilestoneToTaskCommand;
import com.nhnacademy.team4.taskapi.task.application.command.CreateTaskCommand;
import com.nhnacademy.team4.taskapi.task.application.command.UpdateTaskCommand;
import com.nhnacademy.team4.taskapi.task.application.result.TaskDetailResult;

import com.nhnacademy.team4.taskapi.task.web.request.CreateTaskRequest;
import com.nhnacademy.team4.taskapi.task.web.request.UpdateTaskRequest;
import com.nhnacademy.team4.taskapi.task.web.response.TaskDetailResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TaskController {

    private final TaskCommandService taskCommandService;
    private final TaskQueryService taskQueryService;

    @PostMapping("/projects/{projectId}/tasks")
    public ResponseEntity<Void> addTask(
            @PathVariable Long projectId,
            @RequestHeader("X-MEMBER-ID") Long writerMemberId,
            @RequestBody CreateTaskRequest request
    ) {
        CreateTaskCommand command = request.toCreateTaskCommand(projectId, writerMemberId);

        taskCommandService.createTask(command);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<TaskDetailResponse> getTaskDetails(
            @PathVariable Long taskId

    ) {
        TaskDetailResult taskDetail = taskQueryService.getTaskDetail(taskId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(TaskDetailResponse.from(taskDetail));

    }

    @PatchMapping("/tasks/{taskId}")
    public ResponseEntity<Void> updateTask(
            @PathVariable Long taskId,
            @RequestHeader("X-MEMBER-ID") Long writerMemberId,
            @RequestBody UpdateTaskRequest request
    ) {
        UpdateTaskCommand command = request.toUpdateTaskCommand(taskId, writerMemberId);
        taskCommandService.updateTask(command);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long taskId,
            @RequestHeader("X-MEMBER-ID") Long writerMemberId
    ) {
        taskCommandService.deleteTask(taskId, writerMemberId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/tasks/{taskId}/milestone/{milestoneId}")
    public ResponseEntity<Void> setMilestone(
            @RequestHeader("X-MEMBER-ID") Long writerMemberId,
            @PathVariable Long taskId,
            @PathVariable Long milestoneId
    ) {
        AssignMilestoneToTaskCommand command = AssignMilestoneToTaskCommand.toCommand(taskId, milestoneId, writerMemberId);
        taskCommandService.assignMilestoneToTask(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping("/tasks/{taskId}/milestone")
    public ResponseEntity<Void> deleteMilestone(
            @RequestHeader("X-MEMBER-ID") Long writerMemberId,
            @PathVariable Long taskId
    ) {
        taskCommandService.detachMilestoneFromTask(taskId, writerMemberId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
