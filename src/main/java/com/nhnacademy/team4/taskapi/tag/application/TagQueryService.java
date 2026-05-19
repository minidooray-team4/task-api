package com.nhnacademy.team4.taskapi.tag.application;

import com.nhnacademy.team4.taskapi.tag.application.result.TagResult;
import com.nhnacademy.team4.taskapi.tag.application.usecase.GetProjectTagsUseCase;
import com.nhnacademy.team4.taskapi.tag.infrastructure.persistence.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagQueryService implements GetProjectTagsUseCase {

    private final TagRepository tagRepository;

    @Override
    public List<TagResult> getProjectTags(Long projectId, Long requesterMemberId) {
        return tagRepository.findAllByProjectId(projectId)
                .stream()
                .map(TagResult::from)
                .toList();
    }
}
