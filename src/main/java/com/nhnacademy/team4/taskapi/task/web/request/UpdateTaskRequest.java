package com.nhnacademy.team4.taskapi.task.web.request;

public record UpdateTaskRequest(
        String title,
        String content
) {
}
