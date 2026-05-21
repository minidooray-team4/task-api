package com.nhnacademy.team4.taskapi.project.web;

import com.nhnacademy.team4.taskapi.global.exception.BusinessException;
import com.nhnacademy.team4.taskapi.global.exception.ErrorCode;
import com.nhnacademy.team4.taskapi.project.application.ProjectCommandService;
import com.nhnacademy.team4.taskapi.project.application.ProjectQueryService;
import com.nhnacademy.team4.taskapi.project.application.command.AddProjectMemberCommand;
import com.nhnacademy.team4.taskapi.project.application.command.UpdateProjectCommand;
import com.nhnacademy.team4.taskapi.project.application.result.ProjectDetailResult;
import com.nhnacademy.team4.taskapi.project.application.result.ProjectSummaryResult;
import com.nhnacademy.team4.taskapi.project.domain.Status;
import com.nhnacademy.team4.taskapi.project.web.request.CreateProjectRequest;
import com.nhnacademy.team4.taskapi.project.web.request.UpdateProjectRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong; // [수정] anyLong 임포트 추가
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; // [수정] post, put, get, patch 모두 여기서 가져오도록 통합
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
@ActiveProfiles("test")
class ProjectControllerTest {

    private static final String X_MEMBER_ID = "X-MEMBER-ID";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProjectCommandService projectService;

    @MockitoBean
    private ProjectQueryService projectQueryService;

    @Autowired
    private ObjectMapper objectMapper;

    // =========================
    // 1. CREATE PROJECT
    // =========================

    @Test
    void createProject_success() throws Exception {
        CreateProjectRequest request = new CreateProjectRequest("Test Project");

        mockMvc.perform(post("/api/projects")
                        .header(X_MEMBER_ID, 100L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void createProject_fail_validation() throws Exception {
        CreateProjectRequest request = new CreateProjectRequest(" ");

        mockMvc.perform(post("/api/projects")
                        .header(X_MEMBER_ID, 100L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createProject_fail_missingHeader() throws Exception {
        CreateProjectRequest request = new CreateProjectRequest("Test Project");

        mockMvc.perform(post("/api/projects")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    // =========================
    // 2. ADD MEMBER
    // =========================

    @Test
    void addProjectMember_success() throws Exception {
        Mockito.doNothing()
                .when(projectService)
                .addProjectMember(any(AddProjectMemberCommand.class));

        mockMvc.perform(put("/api/projects/1/members/200")
                        .header(X_MEMBER_ID, 100L))
                .andExpect(status().isNoContent());
    }

    @Test
    void addProjectMember_fail_forbidden() throws Exception {
        Mockito.doThrow(new BusinessException(ErrorCode.FORBIDDEN))
                .when(projectService)
                .addProjectMember(any(AddProjectMemberCommand.class));

        mockMvc.perform(put("/api/projects/1/members/200")
                        .header(X_MEMBER_ID, 100L))
                .andExpect(status().isForbidden());
    }

    // =========================
    // 3. UPDATE PROJECT
    // =========================

    @Test
    void updateProject_success() throws Exception {
        UpdateProjectRequest request = new UpdateProjectRequest("new name", Status.ACTIVE);

        Mockito.doNothing()
                .when(projectService)
                .updateProject(any(UpdateProjectCommand.class));

        mockMvc.perform(patch("/api/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(X_MEMBER_ID, 100L)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateProject_fail_validation() throws Exception {
        mockMvc.perform(patch("/api/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(X_MEMBER_ID, 100L))
                .andExpect(status().isBadRequest());
    }

    // =========================
    // 4. GET PROJECT DETAIL
    // =========================

    @Test
    void getProjectDetail_success() throws Exception {
        ProjectSummaryResult summary =
                new ProjectSummaryResult(1L, "Test Project", Status.ACTIVE, 100L);

        ProjectDetailResult result =
                new ProjectDetailResult(summary, List.of(), List.of(), List.of(), List.of());

        Mockito.when(projectQueryService.getProjectDetail(anyLong(), anyLong()))
                .thenReturn(result);

        mockMvc.perform(get("/api/projects/1")
                        .header(X_MEMBER_ID, 100L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project.id").value(1))
                .andExpect(jsonPath("$.project.name").value("Test Project"));
    }

    @Test
    void getProjectDetail_fail_notFound() throws Exception {
        Mockito.when(projectQueryService.getProjectDetail(anyLong(), anyLong()))
                .thenThrow(new BusinessException(ErrorCode.PROJECT_NOT_FOUND));

        mockMvc.perform(get("/api/projects/1")
                        .header(X_MEMBER_ID, 100L))
                .andExpect(status().isNotFound());
    }

    // =========================
    // 5. MY PROJECTS
    // =========================

    @Test
    void findMyProjects_success() throws Exception {
        Mockito.when(projectQueryService.getMyProjects(anyLong()))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/projects")
                        .header(X_MEMBER_ID, 100L))
                .andExpect(status().isOk());
    }

    @Test
    void findMyProjects_fail_missingHeader() throws Exception {
        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isBadRequest());
    }

    // =========================
    // 6. Project MEMBERS
    // =========================

    @Test
    void findProjectMembers_success() throws Exception {
        Mockito.when(projectQueryService.getProjectMembers(anyLong(), anyLong()))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/projects/1/members")
                        .header(X_MEMBER_ID, 100L)
                )
                .andExpect(status().isOk());
    }

    @Test
    void findProjectMembers_fail_notFound() throws Exception {
        Mockito.when(projectQueryService.getProjectMembers(anyLong(), anyLong()))
                .thenThrow(new BusinessException(ErrorCode.PROJECT_NOT_FOUND));

        mockMvc.perform(get("/api/projects/1/members")
                        .header(X_MEMBER_ID, 100L))
                .andExpect(status().isNotFound());
    }

    @Test
    void findProjectMembers_fail_missingHeader() throws Exception {
        mockMvc.perform(get("/api/projects/1/members"))
                .andExpect(status().isBadRequest());
    }
}