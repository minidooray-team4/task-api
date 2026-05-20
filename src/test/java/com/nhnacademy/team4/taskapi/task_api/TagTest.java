package com.nhnacademy.team4.taskapi.task_api;

import com.nhnacademy.team4.taskapi.tags.web.request.CreateTagRequest;
import com.nhnacademy.team4.taskapi.tags.web.request.UpdateTagRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("local") //로컬 환경 설정 사용
@AutoConfigureMockMvc    //실제 서버없이 테스트 가능,목 자동 주입
@Transactional           //자꾸 태그 삭제되서 테스트 실패해서 붙임
public class TagTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("태그 생성 -> 201")
    void CreateTag201() throws Exception {
        CreateTagRequest request=new CreateTagRequest("testTag1");

        mockMvc.perform(post("/api/projects/1/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-MEMBER-ID",100)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("프로젝트 태그 목록 조회 -> 200")
    void GetTags200() throws Exception {
        mockMvc.perform(get("/api/projects/1/tags")
                .header("X-MEMBER-ID",100))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("태그 수정 -> 204")
    void UpdateTag204() throws Exception {
        UpdateTagRequest request=new UpdateTagRequest("testTag2");

        mockMvc.perform(patch("/api/tags/7")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-MEMBER-ID",100)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("태그 삭제 -> 204")
    void DeleteTag204() throws Exception {
        mockMvc.perform(delete("/api/tags/7")
                .header("X-MEMBER-ID",100))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Task에 Tag 연결 -> 204")
    void TaskToTagConnect204() throws Exception {
        mockMvc.perform(put("/api/tasks/1/tags/7")
                .header("X-MEMBER-ID",100))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Task에서 Tag 제거 -> 204")
    void InTeskDeleteTag204() throws Exception {
        mockMvc.perform(delete("/api/tasks/1/tags/7")
                .header("X-MEMBER-ID",100))
                .andExpect(status().isNoContent());
    }
}
