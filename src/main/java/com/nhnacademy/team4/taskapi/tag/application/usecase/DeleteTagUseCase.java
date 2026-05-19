package com.nhnacademy.team4.taskapi.tag.application.usecase;

public interface DeleteTagUseCase {
    void deleteTag(Long tagId,Long requesterMemberId);
}
