package com.nhnacademy.team4.taskapi.milestone.application;

import com.nhnacademy.team4.taskapi.global.exception.BusinessException;
import com.nhnacademy.team4.taskapi.global.exception.ErrorCode;
import com.nhnacademy.team4.taskapi.milestone.application.command.CreateMilestoneCommand;
import com.nhnacademy.team4.taskapi.milestone.application.command.UpdateMilestoneCommand;
import com.nhnacademy.team4.taskapi.milestone.domain.Milestone;
import com.nhnacademy.team4.taskapi.milestone.infrastructure.MilestoneRepository;
import com.nhnacademy.team4.taskapi.project.domain.Project;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectMemberRepository;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MilestoneCommandServiceTest {

    private static final Long PROJECT_ID = 1L;
    private static final Long REQUESTER_MEMBER_ID = 100L;
    private static final Long MILESTONE_ID = 10L;

    @Mock
    private MilestoneRepository milestoneRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMemberRepository projectMemberRepository;

    @InjectMocks
    private MilestoneCommandService milestoneCommandService;

    private Project project;
    private Milestone milestone;

    @BeforeEach
    void setUp() {
        project = Project.create("test_project", REQUESTER_MEMBER_ID);
        ReflectionTestUtils.setField(project, "id", PROJECT_ID);

        milestone = Milestone.create(
                project,
                "test_milestone",
                LocalDate.of(2026, 1, 2)
        );
        ReflectionTestUtils.setField(milestone, "id", MILESTONE_ID);
    }

    @Test
    @DisplayName("createMilestone")
    void testCreateMilestone() {
        CreateMilestoneCommand command = new CreateMilestoneCommand(
                PROJECT_ID,
                REQUESTER_MEMBER_ID,
                "new_milestone",
                LocalDate.of(2026, 2, 3)
        );

        when(projectRepository.findById(command.projectId()))
                .thenReturn(Optional.of(project));
        when(projectMemberRepository.existsByProjectIdAndMemberId(
                command.projectId(),
                command.requesterMemberId()
        )).thenReturn(true);
        when(milestoneRepository.existsByProject_IdAndName(
                command.projectId(),
                command.name()
        )).thenReturn(false);

        milestoneCommandService.createMilestone(command);

        ArgumentCaptor<Milestone> milestoneCaptor = ArgumentCaptor.forClass(Milestone.class);
        verify(milestoneRepository).save(milestoneCaptor.capture());

        Milestone savedMilestone = milestoneCaptor.getValue();
        assertAll(
                () -> assertEquals(project, savedMilestone.getProject()),
                () -> assertEquals(command.projectId(), savedMilestone.getProjectId()),
                () -> assertEquals(command.name(), savedMilestone.getName()),
                () -> assertEquals(command.dueDate(), savedMilestone.getDueDate())
        );
    }

    @Test
    @DisplayName("createMilestone - Project not found")
    void testCreateMilestoneProjectNotFound() {
        CreateMilestoneCommand command = new CreateMilestoneCommand(
                PROJECT_ID,
                REQUESTER_MEMBER_ID,
                "new_milestone",
                LocalDate.of(2026, 2, 3)
        );

        when(projectRepository.findById(command.projectId()))
                .thenReturn(Optional.empty());

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> milestoneCommandService.createMilestone(command)
        );

        assertEquals(ErrorCode.PROJECT_NOT_FOUND, exception.getErrorCode());
        verifyNoInteractions(projectMemberRepository);
        verify(milestoneRepository, never()).existsByProject_IdAndName(anyLong(), anyString());
        verify(milestoneRepository, never()).save(any(Milestone.class));
    }

    @Test
    @DisplayName("createMilestone - Forbidden")
    void testCreateMilestoneForbidden() {
        CreateMilestoneCommand command = new CreateMilestoneCommand(
                PROJECT_ID,
                REQUESTER_MEMBER_ID,
                "new_milestone",
                LocalDate.of(2026, 2, 3)
        );

        when(projectRepository.findById(command.projectId()))
                .thenReturn(Optional.of(project));
        when(projectMemberRepository.existsByProjectIdAndMemberId(
                command.projectId(),
                command.requesterMemberId()
        )).thenReturn(false);

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> milestoneCommandService.createMilestone(command)
        );

        assertEquals(ErrorCode.FORBIDDEN, exception.getErrorCode());
        verify(milestoneRepository, never()).existsByProject_IdAndName(anyLong(), anyString());
        verify(milestoneRepository, never()).save(any(Milestone.class));
    }

    @Test
    @DisplayName("createMilestone - Duplicate milestone name")
    void testCreateMilestoneDuplicateName() {
        CreateMilestoneCommand command = new CreateMilestoneCommand(
                PROJECT_ID,
                REQUESTER_MEMBER_ID,
                "test_milestone",
                LocalDate.of(2026, 2, 3)
        );

        when(projectRepository.findById(command.projectId()))
                .thenReturn(Optional.of(project));
        when(projectMemberRepository.existsByProjectIdAndMemberId(
                command.projectId(),
                command.requesterMemberId()
        )).thenReturn(true);
        when(milestoneRepository.existsByProject_IdAndName(
                command.projectId(),
                command.name()
        )).thenReturn(true);

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> milestoneCommandService.createMilestone(command)
        );

        assertEquals(ErrorCode.MILESTONE_ALREADY_EXISTS, exception.getErrorCode());
        verify(milestoneRepository, never()).save(any(Milestone.class));
    }

    @Test
    @DisplayName("updateMilestone")
    void testUpdateMilestone() {
        UpdateMilestoneCommand command = new UpdateMilestoneCommand(
                MILESTONE_ID,
                REQUESTER_MEMBER_ID,
                "updated_milestone",
                LocalDate.of(2026, 3, 4),
                false
        );

        when(milestoneRepository.findById(command.milestoneId()))
                .thenReturn(Optional.of(milestone));
        when(projectMemberRepository.existsByProjectIdAndMemberId(
                PROJECT_ID,
                command.requesterMemberId()
        )).thenReturn(true);

        milestoneCommandService.updateMilestone(command);

        assertAll(
                () -> assertEquals(command.name(), milestone.getName()),
                () -> assertEquals(command.dueDate(), milestone.getDueDate())
        );
    }

    @Test
    @DisplayName("updateMilestone - Clear due date")
    void testUpdateMilestoneClearDueDate() {
        UpdateMilestoneCommand command = new UpdateMilestoneCommand(
                MILESTONE_ID,
                REQUESTER_MEMBER_ID,
                null,
                null,
                true
        );

        when(milestoneRepository.findById(command.milestoneId()))
                .thenReturn(Optional.of(milestone));
        when(projectMemberRepository.existsByProjectIdAndMemberId(
                PROJECT_ID,
                command.requesterMemberId()
        )).thenReturn(true);

        milestoneCommandService.updateMilestone(command);

        assertAll(
                () -> assertEquals("test_milestone", milestone.getName()),
                () -> assertNull(milestone.getDueDate())
        );
    }

    @Test
    @DisplayName("updateMilestone - Milestone not found")
    void testUpdateMilestoneNotFound() {
        UpdateMilestoneCommand command = new UpdateMilestoneCommand(
                MILESTONE_ID,
                REQUESTER_MEMBER_ID,
                "updated_milestone",
                LocalDate.of(2026, 3, 4),
                false
        );

        when(milestoneRepository.findById(command.milestoneId()))
                .thenReturn(Optional.empty());

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> milestoneCommandService.updateMilestone(command)
        );

        assertEquals(ErrorCode.MILESTONE_NOT_FOUND, exception.getErrorCode());
        verifyNoInteractions(projectMemberRepository);
    }

    @Test
    @DisplayName("updateMilestone - Forbidden")
    void testUpdateMilestoneForbidden() {
        UpdateMilestoneCommand command = new UpdateMilestoneCommand(
                MILESTONE_ID,
                REQUESTER_MEMBER_ID,
                "updated_milestone",
                LocalDate.of(2026, 3, 4),
                false
        );

        when(milestoneRepository.findById(command.milestoneId()))
                .thenReturn(Optional.of(milestone));
        when(projectMemberRepository.existsByProjectIdAndMemberId(
                PROJECT_ID,
                command.requesterMemberId()
        )).thenReturn(false);

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> milestoneCommandService.updateMilestone(command)
        );

        assertEquals(ErrorCode.FORBIDDEN, exception.getErrorCode());
        assertAll(
                () -> assertEquals("test_milestone", milestone.getName()),
                () -> assertEquals(LocalDate.of(2026, 1, 2), milestone.getDueDate())
        );
    }

    @Test
    @DisplayName("updateMilestone - Invalid clear due date request")
    void testUpdateMilestoneInvalidClearDueDateRequest() {
        UpdateMilestoneCommand command = new UpdateMilestoneCommand(
                MILESTONE_ID,
                REQUESTER_MEMBER_ID,
                null,
                LocalDate.of(2026, 3, 4),
                true
        );

        when(milestoneRepository.findById(command.milestoneId()))
                .thenReturn(Optional.of(milestone));
        when(projectMemberRepository.existsByProjectIdAndMemberId(
                PROJECT_ID,
                command.requesterMemberId()
        )).thenReturn(true);

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> milestoneCommandService.updateMilestone(command)
        );

        assertEquals(ErrorCode.INVALID_REQUEST, exception.getErrorCode());
        assertEquals(LocalDate.of(2026, 1, 2), milestone.getDueDate());
    }

    @Test
    @DisplayName("deleteMilestone")
    void testDeleteMilestone() {
        when(milestoneRepository.findById(MILESTONE_ID))
                .thenReturn(Optional.of(milestone));
        when(projectMemberRepository.existsByProjectIdAndMemberId(PROJECT_ID, REQUESTER_MEMBER_ID))
                .thenReturn(true);

        milestoneCommandService.deleteMilestone(MILESTONE_ID, REQUESTER_MEMBER_ID);

        verify(milestoneRepository).delete(milestone);
    }

    @Test
    @DisplayName("deleteMilestone - Milestone not found")
    void testDeleteMilestoneNotFound() {
        when(milestoneRepository.findById(MILESTONE_ID))
                .thenReturn(Optional.empty());

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> milestoneCommandService.deleteMilestone(MILESTONE_ID, REQUESTER_MEMBER_ID)
        );

        assertEquals(ErrorCode.MILESTONE_NOT_FOUND, exception.getErrorCode());
        verifyNoInteractions(projectMemberRepository);
        verify(milestoneRepository, never()).delete(any(Milestone.class));
    }

    @Test
    @DisplayName("deleteMilestone - Forbidden")
    void testDeleteMilestoneForbidden() {
        when(milestoneRepository.findById(MILESTONE_ID))
                .thenReturn(Optional.of(milestone));
        when(projectMemberRepository.existsByProjectIdAndMemberId(PROJECT_ID, REQUESTER_MEMBER_ID))
                .thenReturn(false);

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> milestoneCommandService.deleteMilestone(MILESTONE_ID, REQUESTER_MEMBER_ID)
        );

        assertEquals(ErrorCode.FORBIDDEN, exception.getErrorCode());
        verify(milestoneRepository, never()).delete(any(Milestone.class));
    }
}
