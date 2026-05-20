package com.nhnacademy.team4.taskapi.tags.application;


import com.nhnacademy.team4.taskapi.global.exception.BusinessException;
import com.nhnacademy.team4.taskapi.global.exception.ErrorCode;
import com.nhnacademy.team4.taskapi.project.domain.Project;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectRepository;
import com.nhnacademy.team4.taskapi.tags.application.command.AttachTagToTaskCommand;
import com.nhnacademy.team4.taskapi.tags.application.command.CreateTagCommand;
import com.nhnacademy.team4.taskapi.tags.application.command.DetachTagFromTaskCommand;
import com.nhnacademy.team4.taskapi.tags.application.command.UpdateTagCommand;
import com.nhnacademy.team4.taskapi.tags.application.result.TagResult;
import com.nhnacademy.team4.taskapi.tags.domain.Tag;
import com.nhnacademy.team4.taskapi.tags.persistence.TagRepository;
import com.nhnacademy.team4.taskapi.task.domain.Task;
import com.nhnacademy.team4.taskapi.task.domain.TaskTag;
import com.nhnacademy.team4.taskapi.task.infrastructure.TaskRepository;
import com.nhnacademy.team4.taskapi.task.infrastructure.TaskTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//상태변경 서비스
@Transactional
@Service
@RequiredArgsConstructor
public class TagCommandService {

    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;
    private final TaskTagRepository taskTagRepository;
    private final ProjectRepository projectRepository;


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

    public void deleteTag(Long tagId, Long requesterMemberId) {
        Tag tag=tagRepository.findById(tagId)
                .orElseThrow(()->new RuntimeException("Tag not found"));

        tagRepository.delete(tag);
    }

    public TagResult updateTag(UpdateTagCommand command) {
        Tag tag=tagRepository.findById(command.tagId())
                .orElseThrow(()->new RuntimeException(("Tag not found")));

        tag.rename(command.name());
        return TagResult.from(tag);
    }

    public void attachTagToTask(AttachTagToTaskCommand command) {
        //TODO task_tags 구현 후
        Task task=taskRepository.findById(command.taskId())
                .orElseThrow(()->new BusinessException(ErrorCode.TASK_NOT_FOUND));

        Tag tag=tagRepository.findById(command.tagId())
                .orElseThrow(()->new BusinessException(ErrorCode.TAG_NOT_FOUND));

        if(!task.getProject().getId().equals(tag.getProject().getId())){
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        TaskTag taskTag=TaskTag.create(task,tag,task.getProject().getId());
        taskTagRepository.save(taskTag);

        }

    public void detachTagFromTask(DetachTagFromTaskCommand command) {
        //TODO task_tags 구현 후
        taskTagRepository.deleteByTaskIdAndTagId(command.taskId(),command.tagId());
    }

}
