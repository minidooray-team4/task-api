package com.nhnacademy.team4.taskapi.project.application;

import com.nhnacademy.team4.taskapi.global.exception.BusinessException;
import com.nhnacademy.team4.taskapi.global.exception.ErrorCode;
import com.nhnacademy.team4.taskapi.project.application.command.AddProjectMemberCommand;
import com.nhnacademy.team4.taskapi.project.application.command.CreateProjectCommand;
import com.nhnacademy.team4.taskapi.project.application.command.UpdateProjectCommand;
import com.nhnacademy.team4.taskapi.project.application.result.ProjectSummaryResult;
import com.nhnacademy.team4.taskapi.project.domain.Project;
import com.nhnacademy.team4.taskapi.project.domain.ProjectMembers;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectMemberRepository;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectCommandServiceTest {

    private static final String PROJECT_NAME = "Test Project";
    private static final Long ADMIN_MEMBER_ID = 100L;
    private static final Long MEMBER_ID = 1L;
    private static final Long PROJECT_ID = 10L;
    private static final String NEW_PROJECT_NAME = "New Project";
    private static final String EMPTY_PROJECT_NAME = "Empty Project";

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMemberRepository projectMemberRepository;

    @InjectMocks
    private ProjectCommandService projectCommandService;

    private Project project;


    @BeforeEach
    void setUp() {

        project = Project.create(PROJECT_NAME, ADMIN_MEMBER_ID);

        ReflectionTestUtils.setField(project, "id", PROJECT_ID);

    }

    // =========================
    // 1. ADD PROJECT
    // =========================

    @Test
    void addProjectMember_Success() {
        Long projectId = project.getId();

        AddProjectMemberCommand command = new AddProjectMemberCommand(projectId, MEMBER_ID, ADMIN_MEMBER_ID);

        when(projectRepository.findById(projectId))
                .thenReturn(Optional.of(project));

        when(projectMemberRepository.existsByProjectIdAndMemberId(projectId, MEMBER_ID))
                .thenReturn(Boolean.FALSE);

        assertDoesNotThrow(() -> projectCommandService.addProjectMember(command));

        verify(projectMemberRepository, times(1)).save(any(ProjectMembers.class));

    }

    @Test
    void addProjectMember_Fail_Forbidden() {
        Long projectId = project.getId();

        AddProjectMemberCommand command = new AddProjectMemberCommand(projectId, MEMBER_ID, MEMBER_ID);

        when(projectRepository.findById(projectId))
                .thenReturn(Optional.of(project));


        BusinessException businessException = assertThrows(BusinessException.class, () -> projectCommandService.addProjectMember(command));
        assertEquals(ErrorCode.FORBIDDEN, businessException.getErrorCode());

        verify(projectMemberRepository, never()).save(any(ProjectMembers.class));

    }

    @Test
    void addProjectMember_Fail_Already_Exists() {
        Long projectId = project.getId();

        AddProjectMemberCommand command = new AddProjectMemberCommand(projectId, MEMBER_ID, ADMIN_MEMBER_ID);

        when(projectRepository.findById(projectId))
                .thenReturn(Optional.of(project));

        when(projectMemberRepository.existsByProjectIdAndMemberId(projectId, MEMBER_ID))
                .thenReturn(Boolean.TRUE);

        BusinessException businessException = assertThrows(BusinessException.class, () -> projectCommandService.addProjectMember(command));
        assertEquals(ErrorCode.PROJECT_MEMBER_ALREADY_EXISTS, businessException.getErrorCode());

        verify(projectMemberRepository, never()).save(any(ProjectMembers.class));


    }

    @Test
    void addProjectMember_Fail_ProjectNotFound() {
        Long projectId = project.getId();

        AddProjectMemberCommand command = new AddProjectMemberCommand(projectId, MEMBER_ID, ADMIN_MEMBER_ID);

        when(projectRepository.findById(projectId))
                .thenReturn(Optional.ofNullable(null));

        BusinessException businessException = assertThrows(BusinessException.class, () -> projectCommandService.addProjectMember(command));
        assertEquals(ErrorCode.PROJECT_NOT_FOUND, businessException.getErrorCode());

        verify(projectMemberRepository, never()).save(any(ProjectMembers.class));
    }

    // =========================
    // 2. CREATE PROJECT
    // =========================
    @Test
    void createProject_Success() {
        CreateProjectCommand command = new CreateProjectCommand(ADMIN_MEMBER_ID, PROJECT_NAME);


        when(projectRepository.save(any(Project.class)))
                .thenReturn(project);

        Long savedId = projectCommandService.createProject(command);

        assertEquals(PROJECT_ID, savedId);

        verify(projectRepository, times(1)).save(any(Project.class));
        verify(projectMemberRepository, times(1)).save(any(ProjectMembers.class));

    }


    // =========================
    // 3. UPDATE PROJECT
    // =========================
    @Test
    void updateProject_Success() {
        UpdateProjectCommand command = new UpdateProjectCommand(PROJECT_ID, ADMIN_MEMBER_ID, NEW_PROJECT_NAME, null);

        when(projectRepository.findById(PROJECT_ID))
                .thenReturn(Optional.of(project));

        assertDoesNotThrow(() -> projectCommandService.updateProject(command));

        assertEquals(NEW_PROJECT_NAME, project.getName());
    }

    @Test
    void updateProject_Fail_Forbidden() {
        UpdateProjectCommand command = new UpdateProjectCommand(PROJECT_ID, MEMBER_ID, NEW_PROJECT_NAME, null);

        when(projectRepository.findById(PROJECT_ID))
                .thenReturn(Optional.of(project));

        BusinessException businessException = assertThrows(BusinessException.class, () -> projectCommandService.updateProject(command));

        assertEquals(ErrorCode.FORBIDDEN, businessException.getErrorCode());

    }

    @Test
    void updateProject_Fail_ProjectNotFound() {
        UpdateProjectCommand command = new UpdateProjectCommand(PROJECT_ID, ADMIN_MEMBER_ID, NEW_PROJECT_NAME, null);

        when(projectRepository.findById(PROJECT_ID))
                .thenReturn(Optional.ofNullable(null));

        BusinessException businessException = assertThrows(BusinessException.class, () -> projectCommandService.updateProject(command));
        assertEquals(ErrorCode.PROJECT_NOT_FOUND, businessException.getErrorCode());
    }

    @Test
    void updateProject_Fail_InvalidParameter() {
        UpdateProjectCommand command = new UpdateProjectCommand(PROJECT_ID, ADMIN_MEMBER_ID, "", null);

        when(projectRepository.findById(PROJECT_ID))
                .thenReturn(Optional.of(project));

        BusinessException businessException = assertThrows(BusinessException.class, () -> projectCommandService.updateProject(command));
        assertEquals(ErrorCode.INVALID_REQUEST, businessException.getErrorCode());
    }


}
