package com.nhnacademy.team4.taskapi.tags.application.usecase;

import com.nhnacademy.team4.taskapi.task.application.result.TagResult;

import java.util.List;

public interface GetProjectTagsUseCase {
    List<TagResult> getProjectTags(Long projectId,Long requesterMemberId);
}
