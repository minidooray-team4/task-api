package com.nhnacademy.team4.taskapi.tag.application;


import com.nhnacademy.team4.taskapi.global.exception.BusinessException;
import com.nhnacademy.team4.taskapi.global.exception.ErrorCode;
import com.nhnacademy.team4.taskapi.project.domain.Project;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectRepository;
import com.nhnacademy.team4.taskapi.tag.application.command.AttachTagToTaskCommand;
import com.nhnacademy.team4.taskapi.tag.application.command.CreateTagCommand;
import com.nhnacademy.team4.taskapi.tag.application.command.DetachTagFromTaskCommand;
import com.nhnacademy.team4.taskapi.tag.application.command.UpdateTagCommand;
import com.nhnacademy.team4.taskapi.tag.application.result.TagResult;
import com.nhnacademy.team4.taskapi.tag.application.usecase.*;
import com.nhnacademy.team4.taskapi.tag.domain.Tag;
import com.nhnacademy.team4.taskapi.tag.infrastructure.persistence.TagRepository;
import com.nhnacademy.team4.taskapi.task.domain.Task;
import com.nhnacademy.team4.taskapi.task.domain.TaskTag;
import com.nhnacademy.team4.taskapi.task.infrastructure.TaskRepository;
import com.nhnacademy.team4.taskapi.task.infrastructure.TaskTagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//상태변경 서비스
@Service
@RequiredArgsConstructor
public class TagCommandService implements CreateTagUseCase, UpdateTagUseCase, DeleteTagUseCase, AttachTagToTaskUseCase, DetachTagFromTaskUseCase {

    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;
    private final TaskTagRepository taskTagRepository;
    private final ProjectRepository projectRepository;


    @Override
    public TagResult createTag(CreateTagCommand command) {
        Project project=projectRepository.findById(command.projectId())
                .orElseThrow(()->new BusinessException(ErrorCode.PROJECT_NOT_FOUND));

        if(tagRepository.existsByProjectIdAndName(command.projectId(),command.name())){
            throw new BusinessException(ErrorCode.TAG_ALREADY_EXISTS);
        }

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
        Task task=taskRepository.findById(command.taskId())
                .orElseThrow(()->new BusinessException(ErrorCode.TASK_NOT_FOUND));

        Tag tag=tagRepository.findById(command.tagId())
                .orElseThrow(()->new BusinessException(ErrorCode.TAG_NOT_FOUND));

        TaskTag taskTag=TaskTag.create(task,tag);
        taskTagRepository.save(taskTag);

        }

    @Override
    public void detachTagFromTask(DetachTagFromTaskCommand command) {
        //TODO task_tags 구현 후
        taskTagRepository.deleteByTaskIdAndTagId(command.taskId(),command.tagId());
    }

}
