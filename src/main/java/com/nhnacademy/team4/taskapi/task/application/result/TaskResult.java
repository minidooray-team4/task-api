package com.nhnacademy.team4.taskapi.task.application.result;

import com.nhnacademy.team4.taskapi.task.domain.Task;

public record TaskResult(
        Long id,
        Long projectId,
        String title,
        String content,
        Long writerMemberId,
        Long milestoneId
) {
}
