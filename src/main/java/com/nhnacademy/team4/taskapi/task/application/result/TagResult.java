package com.nhnacademy.team4.taskapi.task.application.result;

import com.nhnacademy.team4.taskapi.tags.domain.Tag;

//나중에 tag 폴더로 이동예정
public record TagResult(
        Long id,
        Long projectId,
        String name
) {
    public static TagResult from(Tag tag){
        return new TagResult(
                tag.getId(),
                tag.getProject().getId(),
                tag.getName()
        );
    }
}