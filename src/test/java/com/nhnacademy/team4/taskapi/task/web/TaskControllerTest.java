package com.nhnacademy.team4.taskapi.task.web;

import com.nhnacademy.team4.taskapi.global.exception.BusinessException;
import com.nhnacademy.team4.taskapi.global.exception.ErrorCode;
import com.nhnacademy.team4.taskapi.task.application.TaskCommandService;
import com.nhnacademy.team4.taskapi.task.application.TaskQueryService;
import com.nhnacademy.team4.taskapi.task.application.command.AssignMilestoneToTaskCommand;
import com.nhnacademy.team4.taskapi.task.application.command.CreateTaskCommand;
import com.nhnacademy.team4.taskapi.task.application.command.UpdateTaskCommand;
import com.nhnacademy.team4.taskapi.task.application.result.TaskDetailResult;
import com.nhnacademy.team4.taskapi.task.web.request.CreateTaskRequest;

import com.nhnacademy.team4.taskapi.task.web.request.UpdateTaskRequest;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TaskController.class)
class TaskControllerTest {

    private static final String X_MEMBER_ID = "X-MEMBER-ID";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    TaskQueryService taskQueryService;

    @MockitoBean
    TaskCommandService taskCommandService;


    // =========================
    // 1. CREATE Task
    // =========================

    @Test
    void createTask_success() throws Exception {

        CreateTaskRequest request = new CreateTaskRequest("Test Task", "Test Content", null);

        mockMvc.perform(post("/api/projects/1/tasks")
                        .header(X_MEMBER_ID, 100)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void createTask_fail_missingHeader() throws Exception {
        CreateTaskRequest request = new CreateTaskRequest("Test Task", "Test Content", null);

        mockMvc.perform(post("/api/projects/1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void createTask_fail_projectNotFound() throws Exception {
        CreateTaskRequest request = new CreateTaskRequest("Test Task", "Test Content", null);

        doThrow(new BusinessException(ErrorCode.PROJECT_NOT_FOUND))
                .when(taskCommandService)
                .createTask(any(CreateTaskCommand.class));

        mockMvc.perform(post("/api/projects/1/tasks")
                        .header(X_MEMBER_ID, 100)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());


    }

    @Test
    void createTask_fail_InvalidName() throws Exception {
        CreateTaskRequest request = new CreateTaskRequest("", "Test Content", null);

        mockMvc.perform(post("/api/projects/1/tasks")
                        .header(X_MEMBER_ID, 100)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());


    }

    // =========================
    // 2. GET TASK DETAIL
    // =========================
    @Test
    void getTaskDetail_success() throws Exception {
        TaskDetailResult result = new TaskDetailResult(
                1L,
                1L,
                "Task Title",
                "Task Content",
                100L,
                null
                , List.of()
                , List.of()
        );

        doReturn(result).when(taskQueryService).getTaskDetail(anyLong(), anyLong());

        mockMvc.perform(get("/api/tasks/1")
                        .header(X_MEMBER_ID, 100))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Task Title"))
                .andExpect(jsonPath("$.content").value("Task Content")
                );

    }

    @Test
    void getTaskDetail_fail_projectNotFound() throws Exception {

        doThrow(new BusinessException(ErrorCode.PROJECT_NOT_FOUND))
                .when(taskQueryService).getTaskDetail(anyLong(), anyLong());

        mockMvc.perform(get("/api/tasks/1")
                        .header(X_MEMBER_ID, 100))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTaskDetail_fail_missingHeader() throws Exception {

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isBadRequest());
    }

    // =========================
    // 3. UPDATE TASK
    // =========================
    @Test
    void updateTask_success_case1() throws Exception {
        UpdateTaskRequest request = new UpdateTaskRequest("Update Title", "Update Content");
        doNothing().when(taskCommandService)
                .updateTask(any(UpdateTaskCommand.class));

        mockMvc.perform(patch("/api/tasks/1")
                        .header(X_MEMBER_ID, 100)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateTask_success_case2() throws Exception {
        UpdateTaskRequest request = new UpdateTaskRequest(null, null);
        doNothing().when(taskCommandService)
                .updateTask(any(UpdateTaskCommand.class));

        mockMvc.perform(patch("/api/tasks/1")
                        .header(X_MEMBER_ID, 100)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateTask_fail_InvalidName() throws Exception {
        UpdateTaskRequest request = new UpdateTaskRequest("", "Update Content");
        doThrow(new BusinessException(ErrorCode.INVALID_REQUEST))
                .when(taskCommandService).updateTask(any(UpdateTaskCommand.class));

        mockMvc.perform(patch("/api/tasks/1")
                        .header(X_MEMBER_ID, 100)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void updateTask_fail_missingHeader() throws Exception {
        UpdateTaskRequest request = new UpdateTaskRequest(null, null);

        doNothing().when(taskCommandService)
                .updateTask(any(UpdateTaskCommand.class));

        mockMvc.perform(patch("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    // =========================
    // 4. DELETE TASK
    // =========================
    @Test
    void deleteTask_success() throws Exception {

        doNothing().when(taskCommandService).deleteTask(anyLong(), anyLong());

        mockMvc.perform(delete("/api/tasks/1")
                        .header(X_MEMBER_ID, 100))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTask_fail_missingHeader() throws Exception {
        doNothing().when(taskCommandService).deleteTask(anyLong(), anyLong());

        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isBadRequest());

    }

    @Test
    void deleteTask_fail_TaskNotFound() throws Exception {
        doThrow(new BusinessException(ErrorCode.TASK_NOT_FOUND))
                .when(taskCommandService).deleteTask(anyLong(), anyLong());

        mockMvc.perform(delete("/api/tasks/1")
                        .header(X_MEMBER_ID, 100))
                .andExpect(status().isNotFound());
    }

    // =========================
    // 5. SET MILESTONE
    // =========================
    @Test
    void setMilestone_success() throws Exception {
        doNothing().when(taskCommandService)
                .assignMilestoneToTask(any(AssignMilestoneToTaskCommand.class));

        mockMvc.perform(put("/api/tasks/1/milestone/1")
                        .header(X_MEMBER_ID, 100))
                .andExpect(status().isNoContent());

    }

    @Test
    void setMilestone_fail_missingHeader() throws Exception {
        doNothing().when(taskCommandService)
                .assignMilestoneToTask(any(AssignMilestoneToTaskCommand.class));

        mockMvc.perform(put("/api/tasks/1/milestone/1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void setMilestone_fail_MilestoneNotFound() throws Exception {
        doThrow(new BusinessException(ErrorCode.MILESTONE_NOT_FOUND))
                .when(taskCommandService)
                .assignMilestoneToTask(any(AssignMilestoneToTaskCommand.class));

        mockMvc.perform(put("/api/tasks/1/milestone/1")
                        .header(X_MEMBER_ID, 100))
                .andExpect(status().isNotFound());
    }

    // =========================
    // 6. DELETE MILESTONE
    // =========================
    @Test
    void deleteMilestone_success() throws Exception {
        doNothing().when(taskCommandService)
                .detachMilestoneFromTask(anyLong(), anyLong());

        mockMvc.perform(delete("/api/tasks/1/milestone")
                        .header(X_MEMBER_ID, 100))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteMilestone_fail_missingHeader() throws Exception {
        doNothing().when(taskCommandService)
                .detachMilestoneFromTask(anyLong(), anyLong());

        mockMvc.perform(delete("/api/tasks/1/milestone"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteMilestone_fail_TaskNotFound() throws Exception {
        doThrow(new BusinessException(ErrorCode.TASK_NOT_FOUND)).when(taskCommandService)
                .detachMilestoneFromTask(anyLong(), anyLong());

        mockMvc.perform(delete("/api/tasks/1/milestone")
                        .header(X_MEMBER_ID, 100))
                .andExpect(status().isNotFound());
    }
}