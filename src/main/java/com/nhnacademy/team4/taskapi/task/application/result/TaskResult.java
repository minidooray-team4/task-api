package com.nhnacademy.team4.taskapi.task.application.result;

import com.nhnacademy.team4.taskapi.project.domain.Project;
import com.nhnacademy.team4.taskapi.task.domain.Task;

public record TaskResult(
        Long id,
        Long projectId,
        String title,
        String content,
        Long writerMemberId,
        Long milestoneId
) {
    public static TaskResult from(Task task) {
        return new TaskResult(
                task.getId(),
                task.getProject().getId(),
                task.getTitle(),
                task.getContent(),
                task.getWriterMemberId(),
                task.getMilestoneId()
        );
    }
}
