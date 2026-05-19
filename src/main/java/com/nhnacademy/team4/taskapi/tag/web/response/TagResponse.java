package com.nhnacademy.team4.taskapi.tag.web.response;

import com.nhnacademy.team4.taskapi.tag.application.result.TagResult;

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
