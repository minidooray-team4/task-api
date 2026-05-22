package com.nhnacademy.team4.taskapi.tags.application;

import com.nhnacademy.team4.taskapi.tags.application.result.TagResult;
import com.nhnacademy.team4.taskapi.tags.persistence.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class TagQueryService {

    private final TagRepository tagRepository;

    public List<TagResult> getProjectTags(Long projectId, Long requesterMemberId) {
        return tagRepository.findAllByProjectId(projectId)
                .stream()
                .map(TagResult::from)
                .toList();
    }
}
