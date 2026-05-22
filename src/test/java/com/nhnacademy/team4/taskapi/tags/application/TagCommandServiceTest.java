package com.nhnacademy.team4.taskapi.tags.application;

import com.nhnacademy.team4.taskapi.project.domain.Project;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectRepository;
import com.nhnacademy.team4.taskapi.tags.application.command.AttachTagToTaskCommand;
import com.nhnacademy.team4.taskapi.tags.application.command.CreateTagCommand;
import com.nhnacademy.team4.taskapi.tags.application.command.DetachTagFromTaskCommand;
import com.nhnacademy.team4.taskapi.tags.application.command.UpdateTagCommand;
import com.nhnacademy.team4.taskapi.tags.domain.Tag;
import com.nhnacademy.team4.taskapi.tags.persistence.TagRepository;
import com.nhnacademy.team4.taskapi.task.domain.Task;
import com.nhnacademy.team4.taskapi.task.domain.TaskTag;
import com.nhnacademy.team4.taskapi.task.infrastructure.TaskRepository;
import com.nhnacademy.team4.taskapi.task.infrastructure.TaskTagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class TagCommandServiceTest {

    //Mock으로 만든 가짜 객체들 자동으로 주입
    @InjectMocks
    TagCommandService tagCommandService;

    @Mock
    TagRepository tagRepository;

    @Mock
    TaskTagRepository taskTagRepository;

    @Mock
    ProjectRepository projectRepository;

    @Mock
    TaskRepository taskRepository;

    @Test
    @DisplayName("태그 생성")
    void createTag() {
        CreateTagCommand command=new CreateTagCommand(1L,100L,"backend");
        Project project=mock(Project.class);

        given(projectRepository.findById(1L)).willReturn(Optional.of(project));
        given(tagRepository.existsByProjectIdAndName(1L,"backend")).willReturn(false);

        tagCommandService.createTag(command);
        verify(tagRepository).save(any(Tag.class));
    }

    @Test
    @DisplayName("태그 삭제")
    void deleteTag() {
        Tag tag=mock(Tag.class);

        given(tagRepository.findById(1L)).willReturn(Optional.of(tag));
        tagCommandService.deleteTag(1L,100L);
        verify(tagRepository).delete(tag);
    }

    @Test
    void updateTag() {
        UpdateTagCommand command=new UpdateTagCommand(1L,100L,"newName");
        Tag tag=mock(Tag.class);
        given(tagRepository.findById(1L)).willReturn(Optional.of(tag));

        tagCommandService.updateTag(command);
        verify(tag).rename("newName");
    }

    @Test
    void attachTagToTask() {
        AttachTagToTaskCommand command=new AttachTagToTaskCommand(1L,1L,100L);
        Task task=mock(Task.class);
        Tag tag=mock(Tag.class);
        Project project=mock(Project.class);

        given(taskRepository.findById(1L)).willReturn(Optional.of(task));
        given(tagRepository.findById(1L)).willReturn(Optional.of(tag));
        given(task.getProject()).willReturn(project);
        given(tag.getProject()).willReturn(project);
        given(project.getId()).willReturn(1L);
        given(taskTagRepository.existsByTask_IdAndTag_Id(1L,1L)).willReturn(false);

        tagCommandService.attachTagToTask(command);
        verify(taskTagRepository).save(any(TaskTag.class));
    }

    @Test
    void detachTagFromTask() {
        DetachTagFromTaskCommand command=new DetachTagFromTaskCommand(1L,1L,100L);
        Task task=mock(Task.class);
        Tag tag=mock(Tag.class);

        given(taskRepository.findById(1L)).willReturn(Optional.of(task));
        given(tagRepository.findById(1L)).willReturn(Optional.of(tag));
        given(taskTagRepository.deleteByTask_IdAndTag_Id(1L,1L)).willReturn(1);

        tagCommandService.detachTagFromTask(command);
        verify(taskTagRepository).deleteByTask_IdAndTag_Id(1L,1L);
    }
}