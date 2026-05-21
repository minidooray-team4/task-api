package com.nhnacademy.team4.taskapi.task.web.request;

import com.nhnacademy.team4.taskapi.task.application.command.UpdateTaskCommand;

public record UpdateTaskRequest(
        String title,
        String content
) {
    public UpdateTaskCommand toUpdateTaskCommand(Long taskId, Long writerMemberId) {
        return new UpdateTaskCommand(taskId,title,content,writerMemberId);
    }
}
