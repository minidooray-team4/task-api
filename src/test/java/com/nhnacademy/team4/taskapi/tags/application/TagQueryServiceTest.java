package com.nhnacademy.team4.taskapi.tags.application;

import com.nhnacademy.team4.taskapi.project.domain.Project;
import com.nhnacademy.team4.taskapi.tags.application.result.TagResult;
import com.nhnacademy.team4.taskapi.tags.domain.Tag;
import com.nhnacademy.team4.taskapi.tags.persistence.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class TagQueryServiceTest {

    @InjectMocks
    TagQueryService tagQueryService;

    @Mock
    TagRepository tagRepository;

    @Test
    void getProjectTags() {
        Project project=mock(Project.class);
        given(project.getId()).willReturn(1L);

        Tag tag1=Tag.create(project,"back1");
        Tag tag2=Tag.create(project,"back2");
        given(tagRepository.findAllByProjectId(1L))
                .willReturn(List.of(tag1,tag2));
        List<TagResult> results=tagQueryService.getProjectTags(1L,100L);

        assertThat(results).hasSize(2);
        assertThat(results.get(0).name()).isEqualTo("back1");
        assertThat(results.get(1).name()).isEqualTo("back2");
    }
}