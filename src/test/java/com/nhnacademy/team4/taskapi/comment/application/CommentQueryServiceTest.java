package com.nhnacademy.team4.taskapi.comment.application;

import com.nhnacademy.team4.taskapi.comment.application.result.CommentResult;
import com.nhnacademy.team4.taskapi.comment.domain.Comment;
import com.nhnacademy.team4.taskapi.comment.persistence.CommentRepository;
import com.nhnacademy.team4.taskapi.global.exception.BusinessException;
import com.nhnacademy.team4.taskapi.global.exception.ErrorCode;
import com.nhnacademy.team4.taskapi.project.domain.Project;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectMemberRepository;
import com.nhnacademy.team4.taskapi.task.domain.Task;
import com.nhnacademy.team4.taskapi.task.infrastructure.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CommentQueryServiceTest {

    private static final Long PROJECT_ID = 10L;
    private static final Long TASK_ID = 20L;
    private static final Long COMMENT_ID = 30L;
    private static final Long REQUESTER_MEMBER_ID = 100L;

    @Mock
    CommentRepository commentRepository;

    @Mock
    TaskRepository taskRepository;

    @Mock
    ProjectMemberRepository projectMemberRepository;

    @InjectMocks
    CommentQueryService commentQueryService;

    private Task task;
    private Comment comment;

    @BeforeEach
    void setUp() {
        Project project = Project.create("project1", REQUESTER_MEMBER_ID);
        ReflectionTestUtils.setField(project, "id", PROJECT_ID);

        task = Task.create("task1", "task content", null, project, REQUESTER_MEMBER_ID);
        ReflectionTestUtils.setField(task, "id", TASK_ID);

        comment = Comment.create(task, "comment content", REQUESTER_MEMBER_ID);
        ReflectionTestUtils.setField(comment, "id", COMMENT_ID);

        // project - task - comment
    }

    @Test
    @DisplayName("getComments")
    void testGetComments() {
        Comment anotherComment = Comment.create(task, "another content", REQUESTER_MEMBER_ID);
        ReflectionTestUtils.setField(anotherComment, "id", 31L);

        given(taskRepository.findById(TASK_ID))
                .willReturn(Optional.of(task));
        given(projectMemberRepository.existsByProjectIdAndMemberId(PROJECT_ID, REQUESTER_MEMBER_ID))
                .willReturn(true);
        given(commentRepository.findByTask_Id(TASK_ID))
                .willReturn(List.of(comment, anotherComment));

        List<CommentResult> results = commentQueryService.getComments(TASK_ID, REQUESTER_MEMBER_ID);

        assertAll(
                () -> assertEquals(2, results.size()),
                () -> assertEquals(COMMENT_ID, results.getFirst().id()),
                () -> assertEquals(TASK_ID, results.getFirst().taskId()),
                () -> assertEquals(REQUESTER_MEMBER_ID, results.getFirst().writerMemberId()),
                () -> assertEquals("comment content", results.getFirst().content()),
                () -> assertEquals(31L, results.get(1).id()),
                () -> assertEquals("another content", results.get(1).content())
        );

        Mockito.verify(commentRepository).findByTask_Id(TASK_ID);
    }

    @Test
    @DisplayName("getComments - Return Empty Comment")
    void testGetCommentsEmptyResult() {
        given(taskRepository.findById(TASK_ID))
                .willReturn(Optional.of(task));
        given(projectMemberRepository.existsByProjectIdAndMemberId(PROJECT_ID, REQUESTER_MEMBER_ID))
                .willReturn(true);
        given(commentRepository.findByTask_Id(TASK_ID))
                .willReturn(List.of());

        List<CommentResult> results = commentQueryService.getComments(TASK_ID, REQUESTER_MEMBER_ID);

        assertTrue(results.isEmpty());
        Mockito.verify(commentRepository).findByTask_Id(TASK_ID);
    }

    @Test
    @DisplayName("getComments - TASK_NOT_FOUND")
    void testGetCommentsTaskNotFound() {
        given(taskRepository.findById(TASK_ID))
                .willReturn(Optional.empty());

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> commentQueryService.getComments(TASK_ID, REQUESTER_MEMBER_ID)
        );

        assertEquals(ErrorCode.TASK_NOT_FOUND, exception.getErrorCode());
        Mockito.verifyNoInteractions(projectMemberRepository);
        Mockito.verify(commentRepository, Mockito.never()).findByTask_Id(Mockito.anyLong());
    }

    @Test
    @DisplayName("getComments - FORBIDDEN")
    void testGetCommentsForbidden() {
        given(taskRepository.findById(TASK_ID))
                .willReturn(Optional.of(task));
        given(projectMemberRepository.existsByProjectIdAndMemberId(PROJECT_ID, REQUESTER_MEMBER_ID))
                .willReturn(false);

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> commentQueryService.getComments(TASK_ID, REQUESTER_MEMBER_ID)
        );

        assertEquals(ErrorCode.FORBIDDEN, exception.getErrorCode());
        Mockito.verify(commentRepository, Mockito.never()).findByTask_Id(Mockito.anyLong());
    }
}
