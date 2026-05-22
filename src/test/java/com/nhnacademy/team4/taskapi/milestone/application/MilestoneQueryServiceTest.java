package com.nhnacademy.team4.taskapi.milestone.application;

import com.nhnacademy.team4.taskapi.global.exception.BusinessException;
import com.nhnacademy.team4.taskapi.global.exception.ErrorCode;
import com.nhnacademy.team4.taskapi.milestone.application.result.MilestoneResult;
import com.nhnacademy.team4.taskapi.milestone.domain.Milestone;
import com.nhnacademy.team4.taskapi.milestone.infrastructure.MilestoneRepository;
import com.nhnacademy.team4.taskapi.project.domain.Project;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MilestoneQueryServiceTest {

    private static final Long PROJECT_ID = 1L;
    private static final Long REQUESTER_MEMBER_ID = 100L;
    private static final Long MILESTONE_ID = 10L;

    @Mock
    private MilestoneRepository milestoneRepository;

    @Mock
    private ProjectMemberRepository projectMemberRepository;

    @InjectMocks
    private MilestoneQueryService milestoneQueryService;

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
    @DisplayName("getProjectMilestones")
    void testGetProjectMilestones() {
        Milestone anotherMilestone = Milestone.create(
                project,
                "another_milestone",
                LocalDate.of(2026, 2, 3)
        );
        ReflectionTestUtils.setField(milestone, "id", MILESTONE_ID);

        when(projectMemberRepository.existsByProjectIdAndMemberId(PROJECT_ID, REQUESTER_MEMBER_ID))
                .thenReturn(true);
        when(milestoneRepository.findByProject_Id(PROJECT_ID))
                .thenReturn(List.of(milestone, anotherMilestone));

        List<MilestoneResult> results = milestoneQueryService.getProjectMilestones(
                PROJECT_ID,
                REQUESTER_MEMBER_ID
        );

        assertEquals(2, results.size());
        assertAll(
                () -> assertEquals(milestone.getId(), results.getFirst().id()),
                () -> assertEquals(PROJECT_ID, results.getFirst().projectId()),
                () -> assertEquals("test_milestone", results.getFirst().name()),
                () -> assertEquals(LocalDate.of(2026, 1, 2), results.getFirst().dueDate()),
                () -> assertEquals(anotherMilestone.getId(), results.get(1).id()),
                () -> assertEquals("another_milestone", results.get(1).name())
        );

        verify(milestoneRepository).findByProject_Id(PROJECT_ID);
    }

    @Test
    @DisplayName("getProjectMilestones - Empty result")
    void testGetProjectMilestonesEmptyResult() {
        when(projectMemberRepository.existsByProjectIdAndMemberId(PROJECT_ID, REQUESTER_MEMBER_ID))
                .thenReturn(true);
        when(milestoneRepository.findByProject_Id(PROJECT_ID))
                .thenReturn(List.of());

        List<MilestoneResult> results = milestoneQueryService.getProjectMilestones(
                PROJECT_ID,
                REQUESTER_MEMBER_ID
        );

        assertTrue(results.isEmpty());
        verify(milestoneRepository).findByProject_Id(PROJECT_ID);
    }

    @Test
    @DisplayName("getProjectMilestones - Forbidden")
    void testGetProjectMilestonesForbidden() {
        when(projectMemberRepository.existsByProjectIdAndMemberId(PROJECT_ID, REQUESTER_MEMBER_ID))
                .thenReturn(false);

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> milestoneQueryService.getProjectMilestones(PROJECT_ID, REQUESTER_MEMBER_ID)
        );

        assertEquals(ErrorCode.FORBIDDEN, exception.getErrorCode());
        verify(milestoneRepository, never()).findByProject_Id(anyLong());
    }
}
