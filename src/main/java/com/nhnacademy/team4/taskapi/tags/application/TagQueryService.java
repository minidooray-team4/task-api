package com.nhnacademy.team4.taskapi.tags.application;

import com.nhnacademy.team4.taskapi.tags.application.result.TagResult;
import com.nhnacademy.team4.taskapi.tags.application.usecase.GetProjectTagsUseCase;
import com.nhnacademy.team4.taskapi.tags.infrastructure.persistence.TagRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagQueryService implements GetProjectTagsUseCase {

    private final TagRepository tagRepository;

    @Override
    public List<TagResult> getProjectTags(Long projectId, Long requesterMemberId) {
        return List.of();
    }
}
