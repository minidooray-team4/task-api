package com.nhnacademy.team4.taskapi.tags.application.result;

import com.nhnacademy.team4.taskapi.tags.domain.Tag;

public record TagResult (
        Long id,
        Long projectId,
        String name
){
    public static TagResult from(Tag tag){
        return new TagResult(
                tag.getId(),
                tag.getProject().getId(),
                tag.getName()
        );
    }
}
