package com.nhnacademy.team4.taskapi.milestone.web;

import com.nhnacademy.team4.taskapi.milestone.application.MilestoneCommandService;
import com.nhnacademy.team4.taskapi.milestone.application.MilestoneQueryService;
import com.nhnacademy.team4.taskapi.milestone.application.result.MilestoneResult;
import com.nhnacademy.team4.taskapi.milestone.web.request.CreateMilestoneRequest;
import com.nhnacademy.team4.taskapi.milestone.web.request.UpdateMilestoneRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MilestoneController.class)
@AutoConfigureMockMvc(addFilters = false)
class MilestoneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonMapper jsonMapper;

    @MockitoBean
    private MilestoneCommandService milestoneCommandService;

    @MockitoBean
    private MilestoneQueryService milestoneQueryService;

    @Test
    @DisplayName("POST /api/projects/{projectId}/milestones - 201 Created")
    void testCreateMilestone() throws Exception {
        CreateMilestoneRequest request = new CreateMilestoneRequest(
                "test_milestone", LocalDate.of(2026, 1, 2)
        );

        mockMvc.perform(post("/api/projects/{projectId}/milestones", 2L)
                        .header("X-MEMBER-ID", 100L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(milestoneCommandService).createMilestone(
                argThat(command ->
                        command.projectId().equals(2L)
                                && command.requesterMemberId().equals(100L)
                                && command.name().equals("test_milestone")
                                && command.dueDate().equals(LocalDate.of(2026, 1, 2))
                ));
    }

    @Test
    @DisplayName("POST /api/projects/{projectId}/milestones - no X-MEMBER-ID")
    void testCreatedMilestoneWithoutMemberId() throws Exception {
        CreateMilestoneRequest request = new CreateMilestoneRequest(
                "test_milestone",
                LocalDate.of(2026, 1, 2)
        );

        mockMvc.perform(post("/api/projects/{projectId}/milestones", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(milestoneCommandService);
    }

    @Test
    @DisplayName("GET /api/projects/{projectId}/milestones - 200 OK")
    void testGetProjectMilestones() throws Exception {
        when(milestoneQueryService.getProjectMilestones(2L, 100L))
                .thenReturn(List.of(
                        new MilestoneResult(1L, 2L, "test_milestone", LocalDate.of(2026,
                                1, 2))
                ));

        mockMvc.perform(get("/api/projects/{projectId}/milestones", 2L)
                        .header("X-MEMBER-ID", 100L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].projectId").value(2L))
                .andExpect(jsonPath("$[0].name").value("test_milestone"))
                .andExpect(jsonPath("$[0].dueDate").value("2026-01-02"));

        verify(milestoneQueryService).getProjectMilestones(2L, 100L);
    }

    @Test
    @DisplayName("PATCH /api/milestones/{milestoneId} - 204 No Content")
    void testUpdateMilestone() throws Exception {
        UpdateMilestoneRequest request = new UpdateMilestoneRequest(
                "updated_milestone",
                LocalDate.of(2026, 2, 3),
                false
        );

        mockMvc.perform(patch("/api/milestones/{milestoneId}", 1L)
                        .header("X-MEMBER-ID", 100L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        verify(milestoneCommandService).updateMilestone(
                argThat(command ->
                        command.milestoneId().equals(1L)
                                && command.requesterMemberId().equals(100L)
                                && command.name().equals("updated_milestone")
                                && command.dueDate().equals(LocalDate.of(2026, 2, 3))
                                && !command.clearDueDate()
                ));
    }


    @Test
    @DisplayName("PATCH /api/milestones/{milestoneId} - clear due date")
    void updateMilestoneClearDueDate() throws Exception {
        UpdateMilestoneRequest request = new UpdateMilestoneRequest(
                null,
                null,
                true
        );

        mockMvc.perform(patch("/api/milestones/{milestoneId}", 1L)
                        .header("X-MEMBER-ID", 100L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        verify(milestoneCommandService).updateMilestone(
                argThat(command ->
                        command.milestoneId().equals(1L)
                                && command.requesterMemberId().equals(100L)
                                && command.name() == null
                                && command.dueDate() == null
                                && command.clearDueDate()
                ));
    }

    @Test
    @DisplayName("DELETE /api/milestones/{milestoneId} - 204 No Content")
    void deleteMilestone() throws Exception {
        mockMvc.perform(delete("/api/milestones/{milestoneId}", 1L)
                        .header("X-MEMBER-ID", 100L))
                .andExpect(status().isNoContent());

        verify(milestoneCommandService).deleteMilestone(1L, 100L);
    }
}
