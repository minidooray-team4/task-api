package com.nhnacademy.team4.taskapi.tags.web;

import com.nhnacademy.team4.taskapi.global.exception.BusinessException;
import com.nhnacademy.team4.taskapi.global.exception.ErrorCode;
import com.nhnacademy.team4.taskapi.tags.application.TagCommandService;
import com.nhnacademy.team4.taskapi.tags.application.TagQueryService;
import com.nhnacademy.team4.taskapi.tags.application.command.AttachTagToTaskCommand;
import com.nhnacademy.team4.taskapi.tags.application.command.CreateTagCommand;
import com.nhnacademy.team4.taskapi.tags.application.command.DetachTagFromTaskCommand;
import com.nhnacademy.team4.taskapi.tags.application.command.UpdateTagCommand;
import com.nhnacademy.team4.taskapi.tags.web.request.CreateTagRequest;
import com.nhnacademy.team4.taskapi.tags.web.request.UpdateTagRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TagController.class)
class TagControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    TagCommandService tagCommandService;

    @MockitoBean
    TagQueryService tagQueryService;

    @Test
    @DisplayName("태그 생성 -> 201")
    void createTag() throws Exception {

        CreateTagRequest request = new CreateTagRequest("testTag1");

        mockMvc.perform(post("/api/projects/1/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-MEMBER-ID", 100)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("태그 삭제 -> 204")
    void deleteTag() throws Exception {
        mockMvc.perform(delete("/api/tags/12")
                        .header("X-MEMBER-ID", 100))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("태그 수정 -> 204")
    void updateTag() throws Exception {
        UpdateTagRequest request = new UpdateTagRequest("testTag2");

        mockMvc.perform(patch("/api/tags/12")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-MEMBER-ID", 100)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("프로젝트 태그 목록 조회 -> 200")
    void GetTags200() throws Exception {
        mockMvc.perform(get("/api/projects/1/tags")
                        .header("X-MEMBER-ID", 100))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Task에 Tag 연결 -> 204")
    void attachTagToTask() throws Exception {
        mockMvc.perform(put("/api/tasks/1/tags/12")
                        .header("X-MEMBER-ID", 100))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Task에서 Tag 제거 -> 204")
    void detachTagFromTask() throws Exception {
        mockMvc.perform(put("/api/tasks/1/tags/12")
                        .header("X-MEMBER-ID", 100))
                .andExpect(status().isNoContent());


        mockMvc.perform(delete("/api/tasks/1/tags/12")
                        .header("X-MEMBER-ID", 100))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("존재하지 않는 프로젝트에 태그 생성 -> 404")
    void CreateTagProjectNotFound404() throws Exception {
        doThrow(new BusinessException(ErrorCode.PROJECT_NOT_FOUND))
                .when(tagCommandService).createTag(any(CreateTagCommand.class));

        CreateTagRequest request = new CreateTagRequest("testTag1");

        mockMvc.perform(post("/api/projects/999/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-MEMBER-ID", 100)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("중복 태그 이름 생성 -> 409")
    void CreateTagDuplicate409() throws Exception {
        doThrow(new BusinessException(ErrorCode.TAG_ALREADY_EXISTS))
                .when(tagCommandService).createTag(any(CreateTagCommand.class));

        CreateTagRequest request = new CreateTagRequest("sangbaek2");

        mockMvc.perform(post("/api/projects/1/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-MEMBER-ID", 100)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("존재하지 않는 태그 수정 -> 404")
    void UpdateTagNotFound404() throws Exception {
        doThrow(new BusinessException(ErrorCode.TAG_NOT_FOUND))
                .when(tagCommandService).updateTag(any(UpdateTagCommand.class));

        UpdateTagRequest request = new UpdateTagRequest("testTag2");

        mockMvc.perform(patch("/api/tags/222")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-MEMBER-ID", 100)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("존재하지 않는 태그 삭제 -> 404")
    void DeleteTagNotFound404() throws Exception {
        doThrow(new BusinessException(ErrorCode.TAG_NOT_FOUND))
                .when(tagCommandService).deleteTag(anyLong(), anyLong());

        mockMvc.perform(delete("/api/tags/999")
                        .header("X-MEMBER-ID", 100))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("존재하지 않는 Task에 Tag 연결 -> 404")
    void AttachTagTaskNotFound404() throws Exception {
        doThrow(new BusinessException(ErrorCode.TASK_NOT_FOUND))
                .when(tagCommandService).attachTagToTask(any(AttachTagToTaskCommand.class));

        mockMvc.perform(put("/api/tasks/999/tags/12")
                        .header("X-MEMBER-ID", 100))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("존재하지 않는 Tag를 Task에 연결 -> 404")
    void AttachTagNotFound404() throws Exception {
        doThrow(new BusinessException(ErrorCode.TAG_NOT_FOUND))
                .when(tagCommandService).attachTagToTask(any(AttachTagToTaskCommand.class));

        mockMvc.perform(put("/api/tasks/1/tags/999")
                        .header("X-MEMBER-ID", 100))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("이미 연결된 Tag 또 연결 -> 409")
    void AttachTagDuplicate409() throws Exception {
        doThrow(new BusinessException(ErrorCode.TAG_ALREADY_EXISTS))
                .when(tagCommandService).attachTagToTask(any(AttachTagToTaskCommand.class));

        mockMvc.perform(put("/api/tasks/1/tags/12")
                        .header("X-MEMBER-ID", 100))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("다른 프로젝트의 Tag를 Task에 연결 -> 403")
    void AttachTagForbidden403() throws Exception {
        doThrow(new BusinessException(ErrorCode.FORBIDDEN))
                .when(tagCommandService)
                .attachTagToTask(any(AttachTagToTaskCommand.class));

        mockMvc.perform(put("/api/tasks/1/tags/6")
                        .header("X-MEMBER-ID", 100))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("존재하지 않는 Task에서 Tag 제거 -> 404")
    void DetachTagTaskNotFound404() throws Exception {
        doThrow(new BusinessException(ErrorCode.TASK_NOT_FOUND))
                .when(tagCommandService).detachTagFromTask(any(DetachTagFromTaskCommand.class));

        mockMvc.perform(delete("/api/tasks/999/tags/12")
                        .header("X-MEMBER-ID", 100))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("존재하지 않는 Tag를 Task에서 제거 -> 404")
    void DetachTagNotFound404() throws Exception {
        doThrow(new BusinessException(ErrorCode.TAG_NOT_FOUND))
                .when(tagCommandService)
                .detachTagFromTask(any(DetachTagFromTaskCommand.class));

        mockMvc.perform(delete("/api/tasks/1/tags/999")
                        .header("X-MEMBER-ID", 100))
                .andExpect(status().isNotFound());
    }
}