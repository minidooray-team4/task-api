package com.nhnacademy.team4.taskapi.comment.web;

import com.nhnacademy.team4.taskapi.comment.application.CommentCommandService;
import com.nhnacademy.team4.taskapi.comment.application.CommentQueryService;
import com.nhnacademy.team4.taskapi.comment.application.command.CreateCommentCommand;
import com.nhnacademy.team4.taskapi.comment.application.command.UpdateCommentCommand;
import com.nhnacademy.team4.taskapi.comment.application.result.CommentResult;
import com.nhnacademy.team4.taskapi.comment.web.request.CreateCommentRequest;
import com.nhnacademy.team4.taskapi.comment.web.request.UpdateCommentRequest;
import com.nhnacademy.team4.taskapi.global.exception.BusinessException;
import com.nhnacademy.team4.taskapi.global.exception.ErrorCode;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    private static final String X_MEMBER_ID = "X-MEMBER-ID";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    CommentCommandService commentCommandService;

    @MockitoBean
    CommentQueryService commentQueryService;

    // =========================
    // 1. CREATE COMMENT
    // =========================

    @Test
    void createComment_success() throws Exception {
        CreateCommentRequest request = new CreateCommentRequest("댓글");

        mockMvc.perform(post("/api/tasks/1/comments")
                        .header(X_MEMBER_ID, 100)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void createComment_fail_InvalidName() throws Exception {
        CreateCommentRequest request = new CreateCommentRequest("");

        mockMvc.perform(post("/api/tasks/1/comments")
                        .header(X_MEMBER_ID, 100)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createComment_fail_missingHeader() throws Exception {
        CreateCommentRequest request = new CreateCommentRequest("댓글");

        mockMvc.perform(post("/api/tasks/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createComment_fail_TaskNotFound() throws Exception {
        CreateCommentRequest request = new CreateCommentRequest("댓글");

        doThrow(new BusinessException(ErrorCode.TASK_NOT_FOUND))
                .when(commentCommandService)
                .createComment(any(CreateCommentCommand.class));

        mockMvc.perform(post("/api/tasks/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(X_MEMBER_ID, 100)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());

    }

    // =========================
    // 2. GET COMMENTS
    // =========================

    @Test
    void getComment_success() throws Exception {

        List<CommentResult> commentResultList = List.of(new CommentResult(1L, 1L, 100L, "Content"));

        doReturn(commentResultList)
                .when(commentQueryService)
                .getComments(anyLong(), anyLong());

        mockMvc.perform(get("/api/tasks/1/comments")
                        .header(X_MEMBER_ID, 100))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].writerMemberId").value(100))
                .andExpect(jsonPath("$.[0].content").value("Content"));


    }

    @Test
    void getComment_fail_TaskNotFound() throws Exception {
        doThrow(new BusinessException(ErrorCode.TASK_NOT_FOUND))
                .when(commentQueryService)
                .getComments(anyLong(), anyLong());

        mockMvc.perform(get("/api/tasks/1/comments")
                        .header(X_MEMBER_ID, 100))
                .andExpect(status().isNotFound());
    }

    @Test
    void getComment_fail_missingHeader() throws Exception {
        mockMvc.perform(get("/api/tasks/1/comments"))
                .andExpect(status().isBadRequest());
    }

    // =========================
    // 3. UPDATE COMMENTS
    // =========================

    @Test
    void updateComment_success() throws Exception {
        UpdateCommentRequest request = new UpdateCommentRequest("Content");
        mockMvc.perform(patch("/api/comments/1")
                        .header(X_MEMBER_ID, 100)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateComment_fail_InvalidContent() throws Exception {
        UpdateCommentRequest request = new UpdateCommentRequest("");
        mockMvc.perform(patch("/api/comments/1")
                        .header(X_MEMBER_ID, 100)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateComment_fail_TaskNotFound() throws Exception {

        UpdateCommentRequest request = new UpdateCommentRequest("Content");

        doThrow(new BusinessException(ErrorCode.TASK_NOT_FOUND))
                .when(commentCommandService)
                .updateComment(any(UpdateCommentCommand.class));

        mockMvc.perform(patch("/api/comments/1")
                        .header(X_MEMBER_ID, 100)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateComment_fail_missingHeader() throws Exception {

        UpdateCommentRequest request = new UpdateCommentRequest("Content");

        doNothing().when(commentCommandService)
                .updateComment(any(UpdateCommentCommand.class));

        mockMvc.perform(patch("/api/comments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }


    // =========================
    // 4.DELETE COMMENTS
    // =========================
    @Test
    void deleteComment_success() throws Exception {

        mockMvc.perform(delete("/api/comments/1")
                        .header(X_MEMBER_ID, 100))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteComment_fail_CommentNotFound() throws Exception {
        doThrow(new BusinessException(ErrorCode.COMMENT_NOT_FOUND))
                .when(commentCommandService)
                .deleteComment(anyLong(), anyLong());

        mockMvc.perform(delete("/api/comments/1")
                        .header(X_MEMBER_ID, 100))
                .andExpect(status().isNotFound());

    }

    @Test
    void deleteComment_fail_missingHeader() throws Exception {

        mockMvc.perform(delete("/api/comments/1"))
                .andExpect(status().isBadRequest());
    }

}