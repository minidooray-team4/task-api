package com.nhnacademy.team4.taskapi.tags.application;


import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectRepository;
import com.nhnacademy.team4.taskapi.tags.application.command.AttachTagToTaskCommand;
import com.nhnacademy.team4.taskapi.tags.application.command.CreateTagCommand;
import com.nhnacademy.team4.taskapi.tags.application.command.DetachTagFromTaskCommand;
import com.nhnacademy.team4.taskapi.tags.application.command.UpdateTagCommand;
import com.nhnacademy.team4.taskapi.tags.application.result.TagResult;
import com.nhnacademy.team4.taskapi.tags.application.usecase.*;
import com.nhnacademy.team4.taskapi.tags.infrastructure.persistence.TagRepository;

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
        return null;
    }

    @Override
    public void deleteTag(Long tagId, Long requesterMemberId) {

    }

    @Override
    public TagResult updateTag(UpdateTagCommand command) {
        return null;
    }

    @Override
    public void attachTagToTask(AttachTagToTaskCommand command) {

    }

    @Override
    public void detachTagFromTask(DetachTagFromTaskCommand command) {

    }
}
