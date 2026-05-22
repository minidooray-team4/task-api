package com.nhnacademy.team4.taskapi.project.application;

import com.nhnacademy.team4.taskapi.global.exception.BusinessException;
import com.nhnacademy.team4.taskapi.global.exception.ErrorCode;
import com.nhnacademy.team4.taskapi.milestone.infrastructure.MilestoneRepository;
import com.nhnacademy.team4.taskapi.project.application.result.ProjectDetailResult;
import com.nhnacademy.team4.taskapi.project.application.result.ProjectMemberResult;
import com.nhnacademy.team4.taskapi.project.application.result.ProjectSummaryResult;
import com.nhnacademy.team4.taskapi.project.domain.Project;
import com.nhnacademy.team4.taskapi.project.domain.ProjectMembers;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectMemberRepository;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectRepository;
import com.nhnacademy.team4.taskapi.tags.persistence.TagRepository;
import com.nhnacademy.team4.taskapi.task.infrastructure.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectQueryServiceTest {

    private static final Long MEMBER_ID = 1L;
    private static final Long PROJECT_ID = 10L;
    private static final String PROJECT_NAME = "Query Test Project";
    private static final Long ADMIN_MEMBER_ID = 100L;

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private ProjectMemberRepository projectMemberRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private MilestoneRepository milestoneRepository;

    @InjectMocks
    private ProjectQueryService projectQueryService;

    private Project project;

    @BeforeEach
    void setUp() {
        project = Project.create(PROJECT_NAME, ADMIN_MEMBER_ID);
        ReflectionTestUtils.setField(project, "id", PROJECT_ID);
    }

    // =========================
    // 1. GET MY PROJECTS
    // =========================
    @Test
    void getMyProjects_Success() {
        when(projectMemberRepository.findProjectsByMemberId(MEMBER_ID))
                .thenReturn(List.of(project));

        List<ProjectSummaryResult> results = projectQueryService.getMyProjects(MEMBER_ID);

        assertNotNull(results);
        assertEquals(1, results.size());
        verify(projectMemberRepository, times(1)).findProjectsByMemberId(MEMBER_ID);
    }

    // =========================
    // 2. GET PROJECT DETAIL
    // =========================
    @Test
    void getProjectDetail_Success() {
        when(projectRepository.findById(PROJECT_ID)).thenReturn(Optional.of(project));

        when(projectMemberRepository.existsByProjectIdAndMemberId(PROJECT_ID, MEMBER_ID))
                .thenReturn(true);

        when(projectMemberRepository.findByProject_Id(PROJECT_ID)).thenReturn(Collections.emptyList());
        when(taskRepository.findByProject_Id(PROJECT_ID)).thenReturn(Collections.emptyList());
        when(tagRepository.findAllByProjectId(PROJECT_ID)).thenReturn(Collections.emptyList());
        when(milestoneRepository.findByProject_Id(PROJECT_ID)).thenReturn(Collections.emptyList());

        ProjectDetailResult result = projectQueryService.getProjectDetail(PROJECT_ID, MEMBER_ID);

        assertNotNull(result);
        verify(projectRepository, times(1)).findById(PROJECT_ID);
        verify(projectMemberRepository, times(1)).existsByProjectIdAndMemberId(PROJECT_ID, MEMBER_ID);
        verify(taskRepository, times(1)).findByProject_Id(PROJECT_ID);
        verify(tagRepository, times(1)).findAllByProjectId(PROJECT_ID);
        verify(milestoneRepository, times(1)).findByProject_Id(PROJECT_ID);
    }

    @Test
    void getProjectDetail_Fail_ProjectNotFound() {
        when(projectRepository.findById(PROJECT_ID)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class,
                () -> projectQueryService.getProjectDetail(PROJECT_ID, MEMBER_ID));
        assertEquals(ErrorCode.PROJECT_NOT_FOUND, exception.getErrorCode());

        verify(projectMemberRepository, never()).existsByProjectIdAndMemberId(any(), any());
        verify(taskRepository, never()).findByProject_Id(any());
    }

    @Test
    void getProjectDetail_Fail_Forbidden() {
        when(projectRepository.findById(PROJECT_ID)).thenReturn(Optional.of(project));

        when(projectMemberRepository.existsByProjectIdAndMemberId(PROJECT_ID, MEMBER_ID))
                .thenReturn(false);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> projectQueryService.getProjectDetail(PROJECT_ID, MEMBER_ID));
        assertEquals(ErrorCode.FORBIDDEN, exception.getErrorCode());

        verify(taskRepository, never()).findByProject_Id(any());
        verify(tagRepository, never()).findAllByProjectId(any());
    }

    // =========================
    // 3. GET PROJECT MEMBERS
    // =========================
    @Test
    void getProjectMembers_Success() {
        when(projectMemberRepository.existsByProjectIdAndMemberId(PROJECT_ID, MEMBER_ID))
                .thenReturn(true);

        ProjectMembers realMember = ProjectMembers.create(project, MEMBER_ID);
        when(projectMemberRepository.findByProject_Id(PROJECT_ID)).thenReturn(List.of(realMember));

        List<ProjectMemberResult> results = projectQueryService.getProjectMembers(PROJECT_ID, MEMBER_ID);

        assertNotNull(results);
        assertEquals(1, results.size());
        verify(projectMemberRepository, times(1)).existsByProjectIdAndMemberId(PROJECT_ID, MEMBER_ID);
        verify(projectMemberRepository, times(1)).findByProject_Id(PROJECT_ID);
    }

    @Test
    void getProjectMembers_Fail_Forbidden() {
        when(projectMemberRepository.existsByProjectIdAndMemberId(PROJECT_ID, MEMBER_ID))
                .thenReturn(false);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> projectQueryService.getProjectMembers(PROJECT_ID, MEMBER_ID));
        assertEquals(ErrorCode.FORBIDDEN, exception.getErrorCode());

        verify(projectMemberRepository, never()).findByProject_Id(PROJECT_ID);
    }

}