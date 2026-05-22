package com.nhnacademy.team4.taskapi.comment.persistence;

import com.nhnacademy.team4.taskapi.comment.domain.Comment;
import com.nhnacademy.team4.taskapi.config.TestJpaConfig;
import com.nhnacademy.team4.taskapi.project.domain.Project;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectRepository;
import com.nhnacademy.team4.taskapi.task.domain.Task;
import com.nhnacademy.team4.taskapi.task.infrastructure.TaskRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import(TestJpaConfig.class)
class CommentRepositoryTest {

    private static final Long PROJECT_ADMIN_ID = 100L;
    private static final Long WRITER_MEMBER_ID = 200L;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    EntityManager entityManager;

    private Task task;
    private Task anotherTask;
    private Comment comment;

    @BeforeEach
    void setUp() {
        Project project = projectRepository.save(Project.create("project1", PROJECT_ADMIN_ID));
        Project anotherProject = projectRepository.save(Project.create("project2", PROJECT_ADMIN_ID));

        task = taskRepository.save(Task.create(
                "task1",
                "task content",
                null,
                project,
                WRITER_MEMBER_ID
        ));
        anotherTask = taskRepository.save(Task.create(
                "task2",
                "another task content",
                null,
                anotherProject,
                WRITER_MEMBER_ID
        ));

        comment = commentRepository.save(Comment.create(task, "comment content", WRITER_MEMBER_ID));
        // project - task - comment
        // anotherProject - anotherTask - [Empty comment]
    }

    @Test
    @DisplayName("findByTask_Id")
    void testFindByTaskId() {
        Comment anotherComment = commentRepository.save(Comment.create(
                task,
                "another content",
                WRITER_MEMBER_ID
        ));
        commentRepository.save(Comment.create(
                anotherTask,
                "other task content",
                WRITER_MEMBER_ID
        ));

        List<Comment> comments = commentRepository.findByTask_Id(task.getId());

        assertAll(
                () -> assertEquals(2, comments.size()),
                () -> assertTrue(comments.stream()
                        .anyMatch(foundComment -> foundComment.getId().equals(comment.getId()))),
                () -> assertTrue(comments.stream()
                        .anyMatch(foundComment -> foundComment.getId().equals(anotherComment.getId()))),
                () -> assertTrue(comments.stream()
                        .allMatch(foundComment -> foundComment.getTaskId().equals(task.getId())))
        );
    }

    @Test
    @DisplayName("findByTask_Id - Return Empty Comment")
    void testFindByTaskIdEmptyResult() {
        List<Comment> comments = commentRepository.findByTask_Id(anotherTask.getId());

        assertTrue(comments.isEmpty());
    }

    @Test
    @DisplayName("findWithTaskById")
    void testFindWithTaskById() {
        entityManager.flush();
        entityManager.clear();

        Optional<Comment> foundComment = commentRepository.findWithTaskById(comment.getId());

        assertTrue(foundComment.isPresent());
        assertAll(
                () -> assertEquals(comment.getId(), foundComment.get().getId()),
                () -> assertEquals(task.getId(), foundComment.get().getTaskId()),
                () -> assertEquals(WRITER_MEMBER_ID, foundComment.get().getWriterMemberId()),
                () -> assertEquals("comment content", foundComment.get().getContent()),
                () -> assertNotNull(foundComment.get().getTask())
        );
    }

    @Test
    @DisplayName("findWithTaskById - Return Empty Comment")
    void testFindWithTaskByIdEmptyResult() {
        Optional<Comment> foundComment = commentRepository.findWithTaskById(999L);

        assertTrue(foundComment.isEmpty());
    }

    @Test
    @DisplayName("AuditingFields")
    void testAuditingFields() {
        Comment savedComment = commentRepository.saveAndFlush(Comment.create(
                task,
                "audit content",
                WRITER_MEMBER_ID
        ));

        assertAll(
                () -> assertNotNull(savedComment.getCreatedAt()),
                () -> assertNotNull(savedComment.getUpdatedAt())
        );
    }
}
