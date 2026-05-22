package com.nhnacademy.team4.taskapi.milestone.infrastructure;

import com.nhnacademy.team4.taskapi.config.TestJpaConfig;
import com.nhnacademy.team4.taskapi.milestone.domain.Milestone;
import com.nhnacademy.team4.taskapi.project.domain.Project;
import com.nhnacademy.team4.taskapi.project.infrastructure.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import(TestJpaConfig.class)
class MilestoneRepositoryTest {

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private ProjectRepository projectRepository;

    private Project testProject1;
    private Project testProject2;
    private Milestone testMilestone;

    @BeforeEach
    void setUp() {
        testProject1 = projectRepository.save(Project.create("test_project1", 1L));
        testProject2 = projectRepository.save(Project.create("test_project2", 2L));
        testMilestone = milestoneRepository.save(Milestone.create(
                testProject1,
                "test_milestone",
                LocalDate.of(2026, 1, 2)
        ));
    }

    @Test
    @DisplayName("findByProject_Id")
    void testFindByProjectId() {
        Milestone anotherMilestone = milestoneRepository.save(Milestone.create(
                testProject1,
                "another_milestone",
                LocalDate.of(2026, 2, 3)
        ));
        milestoneRepository.save(Milestone.create(
                testProject2,
                "other_project_milestone",
                LocalDate.of(2026, 3, 4)
        ));
        // project1 -> test_milestone, another_milestone
        // project2 -> other_project_milestone

        List<Milestone> milestones = milestoneRepository.findByProject_Id(testProject1.getId());

        assertAll(
                () -> assertEquals(2, milestones.size()),
                () -> assertTrue(milestones.stream()
                        .anyMatch(milestone -> milestone.getId().equals(testMilestone.getId()))),
                () -> assertTrue(milestones.stream()
                        .anyMatch(milestone -> milestone.getId().equals(anotherMilestone.getId()))),
                () -> assertTrue(milestones.stream()
                        .allMatch(milestone -> milestone.getProjectId().equals(testProject1.getId())))
        );
    }

    @Test
    @DisplayName("existsByProject_IdAndName")
    void testExistsByProjectIdAndName() {
        assertTrue(milestoneRepository.existsByProject_IdAndName(
                testProject1.getId(),
                "test_milestone"
        ));
        assertFalse(milestoneRepository.existsByProject_IdAndName(
                testProject1.getId(),
                "unknown_milestone"
        ));
        assertFalse(milestoneRepository.existsByProject_IdAndName(
                testProject2.getId(),
                "test_milestone"
        ));
    }

    @Test
    @DisplayName("existsByProject_IdAndName - same name in another project")
    void testExistsByProjectIdAndNameSameNameInAnotherProject() {
        milestoneRepository.save(Milestone.create(
                testProject2,
                "test_milestone",
                LocalDate.of(2026, 4, 5)
        ));

        assertAll(
                () -> assertTrue(milestoneRepository.existsByProject_IdAndName(
                        testProject1.getId(),
                        "test_milestone"
                )),
                () -> assertTrue(milestoneRepository.existsByProject_IdAndName(
                        testProject2.getId(),
                        "test_milestone"
                ))
        );
    }

    @Test
    @DisplayName("findByProject_IdAndId")
    void testFindByProjectIdAndId() {
        Optional<Milestone> milestone = milestoneRepository.findByProject_IdAndId(
                testProject1.getId(),
                testMilestone.getId()
        );

        assertTrue(milestone.isPresent());
        assertAll(
                () -> assertEquals(testMilestone, milestone.get()),
                () -> assertEquals(testMilestone.getId(), milestone.get().getId()),
                () -> assertEquals(testProject1.getId(), milestone.get().getProjectId()),
                () -> assertEquals("test_milestone", milestone.get().getName()),
                () -> assertEquals(LocalDate.of(2026, 1, 2), milestone.get().getDueDate())
        );

        Optional<Milestone> wrongProjectMilestone = milestoneRepository.findByProject_IdAndId(
                testProject2.getId(),
                testMilestone.getId()
        );

        assertTrue(wrongProjectMilestone.isEmpty());
    }

    @Test
    @DisplayName("findById")
    void testFindById() {
        Optional<Milestone> milestone = milestoneRepository.findById(testMilestone.getId());

        assertTrue(milestone.isPresent());
        assertAll(
                () -> assertEquals(testMilestone, milestone.get()),
                () -> assertEquals(testMilestone.getId(), milestone.get().getId()),
                () -> assertEquals(testProject1.getId(), milestone.get().getProjectId()),
                () -> assertEquals("test_milestone", milestone.get().getName()),
                () -> assertEquals(LocalDate.of(2026, 1, 2), milestone.get().getDueDate())
        );
    }

    @Test
    @DisplayName("create - AuditingFields")
    void testAuditingFields() {
        Milestone milestone = milestoneRepository.saveAndFlush(Milestone.create(
                testProject1,
                "audit_milestone",
                LocalDate.of(2026, 5, 6)
        ));

        assertAll(
                () -> assertNotNull(milestone.getCreatedAt()),
                () -> assertNotNull(milestone.getUpdatedAt())
        );
    }
}
