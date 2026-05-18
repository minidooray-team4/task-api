package com.nhnacademy.team4.taskapi.tags.web.response;

import com.nhnacademy.team4.taskapi.task.application.result.TagResult;

public record TagResponse(
        Long id,
        Long projectId,
        String name
) {
    public static TagResponse from(TagResult tagResult){
        return new TagResponse(
                tagResult.id(),
                tagResult.projectId(),
                tagResult.name()
        );
    }
}
