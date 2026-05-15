package com.nhnacademy.team4.taskapi.task.application.command;

public record UpdateTaskCommand(
        Long taskId,
        String title,
        String content
) {
}
