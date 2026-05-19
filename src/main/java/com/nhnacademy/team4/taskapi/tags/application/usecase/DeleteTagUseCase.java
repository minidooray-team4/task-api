package com.nhnacademy.team4.taskapi.tags.application.usecase;

public interface DeleteTagUseCase {
    void deleteTag(Long tagId,Long requesterMemberId);
}
