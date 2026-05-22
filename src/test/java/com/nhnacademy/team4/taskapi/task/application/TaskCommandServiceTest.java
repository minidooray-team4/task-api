package com.nhnacademy.team4.taskapi.task.application;

import com.nhnacademy.team4.taskapi.global.exception.BusinessException;
import com.nhnacademy.team4.taskapi.milestone.domain.Milestone;
import com.nhnacademy.team4.taskapi.milestone.infrastructure.MilestoneRepository;
import com.nhnacademy.team4.taskapi.project.domain.Project;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectMemberRepository;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectRepository;
import com.nhnacademy.team4.taskapi.tags.persistence.TagRepository;
import com.nhnacademy.team4.taskapi.task.application.command.AssignMilestoneToTaskCommand;
import com.nhnacademy.team4.taskapi.task.application.command.CreateTaskCommand;
import com.nhnacademy.team4.taskapi.task.application.command.UpdateTaskCommand;
import com.nhnacademy.team4.taskapi.task.domain.Task;
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
class TaskCommandServiceTest {

    private static final Long PROJECT_ID = 1L;
    private static final Long TASK_ID = 1L;
    private static final Long MEMBER_ID = 100L;
    private static final Long MILESTONE_ID = 10L;

    @InjectMocks
    TaskCommandService taskCommandService;

    @Mock
    TaskRepository taskRepository;

    @Mock
    TagRepository tagRepository;

    @Mock
    TaskTagRepository taskTagRepository;

    @Mock
    ProjectRepository projectRepository;

    @Mock
    MilestoneRepository milestoneRepository;

    @Mock
    ProjectMemberRepository projectMemberRepository;

    @Test
    @DisplayName("마일스톤 지정")
    void assignMilestoneToTask() {
        Task task=mock(Task.class);
        Milestone milestone=mock(Milestone.class);
        AssignMilestoneToTaskCommand command=new AssignMilestoneToTaskCommand(TASK_ID,MILESTONE_ID,MEMBER_ID);

        given(taskRepository.findById(TASK_ID)).willReturn(Optional.of(task));
        given(task.getProjectId()).willReturn(PROJECT_ID);
        given(projectMemberRepository.existsByProjectIdAndMemberId(PROJECT_ID,MEMBER_ID)).willReturn(true);
        given(milestoneRepository.findByProject_IdAndId(PROJECT_ID,MILESTONE_ID)).willReturn(Optional.of(milestone));

        taskCommandService.assignMilestoneToTask(command);
        verify(task).assignMilestone(milestone);
    }

    @Test
    @DisplayName("마일스톤 지정 - 태스크 없음 예외")
    void assignMilestoneToTask_taskNotFound() {
        AssignMilestoneToTaskCommand command = new AssignMilestoneToTaskCommand(999L, MILESTONE_ID, MEMBER_ID);
        given(taskRepository.findById(999L)).willReturn(Optional.empty());
        assertThrows(BusinessException.class,
                ()->taskCommandService.assignMilestoneToTask(command));
    }

    @Test
    @DisplayName("태스크 생성")
    void createTask() {
        CreateTaskCommand command=new CreateTaskCommand(PROJECT_ID,MEMBER_ID,"title","content",null);
        Project project=mock(Project.class);

        given(projectRepository.findById(PROJECT_ID)).willReturn(Optional.of(project));
        given(projectMemberRepository.existsByProjectIdAndMemberId(PROJECT_ID,MEMBER_ID)).willReturn(true);

        taskCommandService.createTask(command);

        verify(taskRepository).save(any(Task.class));
    }

    @Test
    @DisplayName("태스크 생성 - 프로젝트 없음 예외")
    void createTask_projectNotFound(){
        CreateTaskCommand command=new CreateTaskCommand(999L,MEMBER_ID,"title","content",null);
        given(projectRepository.findById(999L)).willReturn(Optional.empty());

        assertThrows(BusinessException.class,
                ()->taskCommandService.createTask(command));
    }

    @Test
    @DisplayName("태스크 생성 - 권한 없음 예외")
    void createTask_forbbiden(){
        CreateTaskCommand command=new CreateTaskCommand(PROJECT_ID,MEMBER_ID,"title","content",null);
        Project project=mock(Project.class);

        given(projectRepository.findById(PROJECT_ID)).willReturn(Optional.of(project));
        given(projectMemberRepository.existsByProjectIdAndMemberId(PROJECT_ID,MEMBER_ID)).willReturn(false);

        assertThrows(BusinessException.class,
                ()->taskCommandService.createTask(command));
    }

    @Test
    @DisplayName("태스크 수정")
    void updateTask() {
        UpdateTaskCommand command=new UpdateTaskCommand(TASK_ID,"title","content",MEMBER_ID);
        Task task=mock(Task.class);

        given(taskRepository.findById(TASK_ID)).willReturn(Optional.of(task));
        given(task.getProjectId()).willReturn(PROJECT_ID);
        given(projectMemberRepository.existsByProjectIdAndMemberId(PROJECT_ID,MEMBER_ID)).willReturn(true);

        taskCommandService.updateTask(command);
        verify(task).update("title","content");
    }

    @Test
    @DisplayName("태스크 수정 - 없음 에러")
    void updateTask_taskNotFound(){
        UpdateTaskCommand command=new UpdateTaskCommand(999L,"title","content",MEMBER_ID);
        given(taskRepository.findById(999L)).willReturn(Optional.empty());

        assertThrows(BusinessException.class,
                ()->taskCommandService.updateTask(command));
    }

    @Test
    @DisplayName("마일스톤 제거")
    void detachMilestoneFromTask() {
        Task task=mock(Task.class);

        given(taskRepository.findById(TASK_ID)).willReturn(Optional.of(task));
        given(task.getProjectId()).willReturn(PROJECT_ID);
        given(projectMemberRepository.existsByProjectIdAndMemberId(PROJECT_ID,MEMBER_ID)).willReturn(true);

        taskCommandService.detachMilestoneFromTask(TASK_ID,MEMBER_ID);
        verify(task).detachMilestone();
    }

    @Test
    @DisplayName("마일스톤 제거 - 태스크 없음 예외")
    void detachMilestoneFromTask_taskNotFound(){
        given(taskRepository.findById(999L)).willReturn(Optional.empty());
        assertThrows(BusinessException.class,
                ()->taskCommandService.detachMilestoneFromTask(999L,MEMBER_ID));
    }

    @Test
    @DisplayName("태스크 제거")
    void deleteTask() {
        Task task=mock(Task.class);
        given(taskRepository.findById(TASK_ID)).willReturn(Optional.of(task));
        given(task.getProjectId()).willReturn(PROJECT_ID);
        given(projectMemberRepository.existsByProjectIdAndMemberId(PROJECT_ID,MEMBER_ID)).willReturn(true);

        taskCommandService.deleteTask(TASK_ID,MEMBER_ID);
        verify(taskRepository).deleteById(TASK_ID);
    }

    @Test
    @DisplayName("태스크 제거 - 태스크 없음 예외")
    void deleteTask_taskNotFound(){
        given(taskRepository.findById(999L)).willReturn(Optional.empty());
        assertThrows(BusinessException.class,
                ()->taskCommandService.deleteTask(999L,MEMBER_ID));
    }
}