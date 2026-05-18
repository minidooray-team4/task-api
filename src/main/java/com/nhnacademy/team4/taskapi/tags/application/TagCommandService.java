package com.nhnacademy.team4.taskapi.tags.application;

import com.nhnacademy.team4.taskapi.project.application.infrastructure.ProjectRepository;
import com.nhnacademy.team4.taskapi.project.domain.Project;
import com.nhnacademy.team4.taskapi.tags.application.command.AttachTagToTaskCommand;
import com.nhnacademy.team4.taskapi.tags.application.command.CreateTagCommand;
import com.nhnacademy.team4.taskapi.tags.application.command.DetachTagFromTaskCommand;
import com.nhnacademy.team4.taskapi.tags.application.command.UpdateTagCommand;
import com.nhnacademy.team4.taskapi.tags.application.usecase.*;
import com.nhnacademy.team4.taskapi.tags.domain.Tag;
import com.nhnacademy.team4.taskapi.tags.infrastructure.persistence.TagRepository;
import com.nhnacademy.team4.taskapi.task.application.result.TagResult;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//상태변경 서비스
@Service
@RequiredArgsConstructor
public class TagCommandService implements CreateTagUseCase, UpdateTagUseCase, DeleteTagUseCase, AttachTagToTaskUseCase, DetachTagFromTaskUseCase {

    private final TagRepository tagRepository;
    private final ProjectRepository projectRepository;


    @Override
    public TagResult createTag(CreateTagCommand command) {
        Project project=projectRepository.findById(command.projectId())
                .orElseThrow(()->new RuntimeException("Project not found"));

        Tag tag=Tag.create(project,command.name());
        Tag savedTag=tagRepository.save(tag);
        return TagResult.from(savedTag);
    }

    @Override
    public void deleteTag(Long tagId, Long requesterMemberId) {
        Tag tag=tagRepository.findById(tagId)
                .orElseThrow(()->new RuntimeException("Tag not found"));

        tagRepository.delete(tag);
    }

    @Transactional
    @Override
    public TagResult updateTag(UpdateTagCommand command) {
        Tag tag=tagRepository.findById(command.tagId())
                .orElseThrow(()->new RuntimeException(("Tag not found")));

        tag.rename(command.name());
        return TagResult.from(tag);
    }

    @Override
    public void attachTagToTask(AttachTagToTaskCommand command) {
        //TODO task_tags 구현 후
    }

    @Override
    public void detachTagFromTask(DetachTagFromTaskCommand command) {
        //TODO task_tags 구현 후
    }
}
