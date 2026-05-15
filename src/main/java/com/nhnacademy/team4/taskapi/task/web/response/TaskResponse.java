package com.nhnacademy.team4.taskapi.task.web.response;

public record TaskResponse(
        Long id,
        Long projectId,
        String title,
        String content,
        Long writerMemberId,
        Long milestoneId
) {

}
