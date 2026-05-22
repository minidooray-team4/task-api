package com.nhnacademy.team4.taskapi.task.application;

import com.nhnacademy.team4.taskapi.comment.persistence.CommentRepository;
import com.nhnacademy.team4.taskapi.global.exception.BusinessException;
import com.nhnacademy.team4.taskapi.project.domain.Project;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectMemberRepository;
import com.nhnacademy.team4.taskapi.tags.persistence.TagRepository;
import com.nhnacademy.team4.taskapi.task.application.command.GetProjectTasksQuery;
import com.nhnacademy.team4.taskapi.task.application.result.TaskDetailResult;
import com.nhnacademy.team4.taskapi.task.application.result.TaskSummaryResult;
import com.nhnacademy.team4.taskapi.task.domain.Task;
import com.nhnacademy.team4.taskapi.task.infrastructure.TaskRepository;
import com.nhnacademy.team4.taskapi.task.infrastructure.TaskTagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class TaskQueryServiceTest {

    private static final Long PROJECT_ID=1L;
    private static final Long MILESTONE_ID=10L;
    private static final Long REQUESTER_MEMBER_ID=100L;

    @InjectMocks
    TaskQueryService taskQueryService;

    @Mock
    TaskRepository taskRepository;

    @Mock
    TagRepository tagRepository;

    @Mock
    TaskTagRepository taskTagRepository;

    @Mock
    ProjectMemberRepository projectMemberRepository;

    @Mock
    CommentRepository commentRepository;

    @Test
    @DisplayName("프로젝트 태스크 목록 조회")
    void getProjectTasks() {
        GetProjectTasksQuery query=new GetProjectTasksQuery(PROJECT_ID,MILESTONE_ID,null,REQUESTER_MEMBER_ID);
        given(projectMemberRepository.existsByProjectIdAndMemberId(PROJECT_ID,REQUESTER_MEMBER_ID)).willReturn(true);
        given(taskRepository.findByProject_Id(PROJECT_ID)).willReturn(List.of());

        List<TaskSummaryResult> results=taskQueryService.getProjectTasks(query);

        assertNotNull(results);
    }

    @Test
    @DisplayName("프로젝트 태스크 목록 조회 - 권한 없음 예외")
    void getProjectTasks_forbidden(){
        GetProjectTasksQuery query = new GetProjectTasksQuery(PROJECT_ID, MILESTONE_ID, null, REQUESTER_MEMBER_ID);
        given(projectMemberRepository.existsByProjectIdAndMemberId(PROJECT_ID, REQUESTER_MEMBER_ID)).willReturn(false);

        assertThrows(BusinessException.class,
                () -> taskQueryService.getProjectTasks(query));
    }

    @Test
    @DisplayName("태스크 상세 조회 - 성공")
    void getTaskDetail() {
        Task task = mock(Task.class);
        Project project=mock(Project.class);

        given(taskRepository.findById(1L)).willReturn(Optional.of(task));
        given(task.getProjectId()).willReturn(PROJECT_ID);
        given(task.getProject()).willReturn(project);
        given(project.getId()).willReturn(PROJECT_ID);
        given(projectMemberRepository.existsByProjectIdAndMemberId(PROJECT_ID, REQUESTER_MEMBER_ID)).willReturn(true);
        given(taskTagRepository.findByTask_Id(1L)).willReturn(List.of());
        given(commentRepository.findByTask_Id(1L)).willReturn(List.of());

        TaskDetailResult result = taskQueryService.getTaskDetail(1L, REQUESTER_MEMBER_ID);

        assertNotNull(result);
    }

    @Test
    @DisplayName("태스크 상세 조회 - 테스크 없음 예외")
    void getTaskDetail_notFound(){
        given(taskRepository.findById(999L)).willReturn(Optional.empty());
        assertThrows(BusinessException.class,
                ()-> taskQueryService.getTaskDetail(999L,REQUESTER_MEMBER_ID));
    }

    @Test
    @DisplayName("태스크 상세 조회 - 태스크 권한 x")
    void getTaskDetail_forbidden(){
        Task task=mock(Task.class);
        given(taskRepository.findById(1L)).willReturn(Optional.of(task));
        given(task.getProjectId()).willReturn(PROJECT_ID);
        given(projectMemberRepository.existsByProjectIdAndMemberId(PROJECT_ID,REQUESTER_MEMBER_ID)).willReturn(false);

        assertThrows(BusinessException.class,
                ()->taskQueryService.getTaskDetail(1L,REQUESTER_MEMBER_ID));
    }
}