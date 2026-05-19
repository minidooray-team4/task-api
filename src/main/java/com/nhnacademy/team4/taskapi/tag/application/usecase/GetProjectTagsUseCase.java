package com.nhnacademy.team4.taskapi.tag.application.usecase;

import com.nhnacademy.team4.taskapi.tag.application.result.TagResult;

import java.util.List;

public interface GetProjectTagsUseCase {
    List<TagResult> getProjectTags(Long projectId, Long requesterMemberId);
}
