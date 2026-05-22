package com.nhnacademy.team4.taskapi.comment.application;

import com.nhnacademy.team4.taskapi.comment.application.command.CreateCommentCommand;
import com.nhnacademy.team4.taskapi.comment.application.command.UpdateCommentCommand;
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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CommentCommandServiceTest {

    private static final Long PROJECT_ID = 10L;
    private static final Long TASK_ID = 20L;
    private static final Long COMMENT_ID = 30L;
    private static final Long REQUESTER_MEMBER_ID = 100L;
    private static final Long OTHER_MEMBER_ID = 200L;

    @Mock
    CommentRepository commentRepository;

    @Mock
    TaskRepository taskRepository;

    @Mock
    ProjectMemberRepository projectMemberRepository;

    @InjectMocks
    CommentCommandService commentCommandService;

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
    @DisplayName("createComment")
    void testCreateComment() {
        CreateCommentCommand command = new CreateCommentCommand(
                TASK_ID,
                REQUESTER_MEMBER_ID,
                "new comment"
        );

        given(taskRepository.findById(command.taskId()))
                .willReturn(Optional.of(task));
        given(projectMemberRepository.existsByProjectIdAndMemberId(
                PROJECT_ID,
                command.requesterMemberId()
        )).willReturn(true);

        commentCommandService.createComment(command);

        ArgumentCaptor<Comment> commentCaptor = ArgumentCaptor.forClass(Comment.class);
        Mockito.verify(commentRepository).save(commentCaptor.capture());

        Comment savedComment = commentCaptor.getValue();
        assertAll(
                () -> assertEquals(task, savedComment.getTask()),
                () -> assertEquals(TASK_ID, savedComment.getTaskId()),
                () -> assertEquals(command.requesterMemberId(), savedComment.getWriterMemberId()),
                () -> assertEquals(command.content(), savedComment.getContent())
        );
    }

    @Test
    @DisplayName("createComment - TASK_NOT_FOUND")
    void testCreateCommentTaskNotFound() {
        CreateCommentCommand command = new CreateCommentCommand(
                TASK_ID,
                REQUESTER_MEMBER_ID,
                "new comment"
        );

        given(taskRepository.findById(command.taskId()))
                .willReturn(Optional.empty());

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> commentCommandService.createComment(command)
        );

        assertEquals(ErrorCode.TASK_NOT_FOUND, exception.getErrorCode());
        Mockito.verifyNoInteractions(projectMemberRepository);
        Mockito.verify(commentRepository, Mockito.never()).save(Mockito.any(Comment.class));
    }

    @Test
    @DisplayName("createComment - FORBIDDEN")
    void testCreateCommentForbidden() {
        CreateCommentCommand command = new CreateCommentCommand(
                TASK_ID,
                REQUESTER_MEMBER_ID,
                "new comment"
        );

        given(taskRepository.findById(command.taskId()))
                .willReturn(Optional.of(task));
        given(projectMemberRepository.existsByProjectIdAndMemberId(
                PROJECT_ID,
                command.requesterMemberId()
        )).willReturn(false);

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> commentCommandService.createComment(command)
        );

        assertEquals(ErrorCode.FORBIDDEN, exception.getErrorCode());
        Mockito.verify(commentRepository, Mockito.never()).save(Mockito.any(Comment.class));
    }

    @Test
    @DisplayName("updateComment")
    void testUpdateComment() {
        UpdateCommentCommand command = new UpdateCommentCommand(
                COMMENT_ID,
                REQUESTER_MEMBER_ID,
                "updated content"
        );

        given(commentRepository.findWithTaskById(command.commentId()))
                .willReturn(Optional.of(comment));

        commentCommandService.updateComment(command);

        assertEquals(command.content(), comment.getContent());
    }

    @Test
    @DisplayName("updateComment - COMMENT_NOT_FOUND")
    void testUpdateCommentNotFound() {
        UpdateCommentCommand command = new UpdateCommentCommand(
                COMMENT_ID,
                REQUESTER_MEMBER_ID,
                "updated content"
        );

        given(commentRepository.findWithTaskById(command.commentId()))
                .willReturn(Optional.empty());

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> commentCommandService.updateComment(command)
        );

        assertEquals(ErrorCode.COMMENT_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("updateComment - FORBIDDEN")
    void testUpdateCommentForbidden() {
        UpdateCommentCommand command = new UpdateCommentCommand(
                COMMENT_ID,
                OTHER_MEMBER_ID,
                "updated content"
        );

        given(commentRepository.findWithTaskById(command.commentId()))
                .willReturn(Optional.of(comment));

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> commentCommandService.updateComment(command)
        );

        assertAll(
                () -> assertEquals(ErrorCode.FORBIDDEN, exception.getErrorCode()),
                () -> assertEquals("comment content", comment.getContent())
        );
    }

    @Test
    @DisplayName("deleteComment")
    void testDeleteComment() {
        given(commentRepository.findWithTaskById(COMMENT_ID))
                .willReturn(Optional.of(comment));

        commentCommandService.deleteComment(COMMENT_ID, REQUESTER_MEMBER_ID);

        Mockito.verify(commentRepository).delete(comment);
    }

    @Test
    @DisplayName("deleteComment - COMMENT_NOT_FOUND")
    void testDeleteCommentNotFound() {
        given(commentRepository.findWithTaskById(COMMENT_ID))
                .willReturn(Optional.empty());

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> commentCommandService.deleteComment(COMMENT_ID, REQUESTER_MEMBER_ID)
        );

        assertEquals(ErrorCode.COMMENT_NOT_FOUND, exception.getErrorCode());
        Mockito.verify(commentRepository, Mockito.never()).delete(Mockito.any(Comment.class));
    }

    @Test
    @DisplayName("deleteComment - FORBIDDEN")
    void testDeleteCommentForbidden() {
        given(commentRepository.findWithTaskById(COMMENT_ID))
                .willReturn(Optional.of(comment));

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> commentCommandService.deleteComment(COMMENT_ID, OTHER_MEMBER_ID)
        );

        assertEquals(ErrorCode.FORBIDDEN, exception.getErrorCode());
        Mockito.verify(commentRepository, Mockito.never()).delete(Mockito.any(Comment.class));
    }
}
