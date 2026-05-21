package com.nhnacademy.team4.taskapi.project.infrastructure;

import com.nhnacademy.team4.taskapi.project.domain.Project;

import com.nhnacademy.team4.taskapi.project.domain.ProjectMembers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProjectMemberRepositoryTest {
    private static final Long PROJECT_ADMIN_ID1 = 1L;
    private static final Long PROJECT_ADMIN_ID2 = 2L;

    private static final Long PROJECT_MEMBER_ID1 = 300L;
    private static final Long PROJECT_MEMBER_ID2 = 400L;

    @Autowired
    private ProjectMemberRepository projectMemberRepository;

    @Autowired
    private ProjectRepository projectRepository;

    private Project createProject(Long adminMemberId) {
        Project project = Project.create("테스트 프로젝트", adminMemberId);

        return projectRepository.save(project);
    }

    private void createMember(Project project, Long memberId) {
        ProjectMembers projectMembers = ProjectMembers.create(project, memberId);

        projectMemberRepository.save(projectMembers);
    }


    @Test
    void exists_true() {
        Project project = createProject(PROJECT_ADMIN_ID1);
        createMember(project, PROJECT_MEMBER_ID1);


        boolean result =
                projectMemberRepository.existsByProjectIdAndMemberId(project.getId(), PROJECT_MEMBER_ID1);

        assertTrue(result);
    }

    @Test
    void exists_false() {
        Project project = createProject(PROJECT_ADMIN_ID1);

        boolean result = projectMemberRepository.existsByProjectIdAndMemberId(project.getId(), PROJECT_MEMBER_ID1);

        assertFalse(result);
    }

    @Test
    void findByProjectId() {
        Project project = createProject(PROJECT_ADMIN_ID1);

        createMember(project, PROJECT_MEMBER_ID1);
        createMember(project, PROJECT_MEMBER_ID2);

        List<ProjectMembers> result = projectMemberRepository.findByProject_Id(project.getId());

        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertTrue(
                        result.stream().anyMatch(
                                member -> member.getMemberId().equals(PROJECT_MEMBER_ID1)
                        )),
                () -> assertTrue(
                        result.stream().anyMatch(
                                member -> member.getMemberId().equals(PROJECT_MEMBER_ID2)
                        ))
        );

    }

    @Test
    void findByMemberId() {
        Project project1 = createProject(PROJECT_ADMIN_ID1);
        Project project2 = createProject(PROJECT_ADMIN_ID2);

        createMember(project1, PROJECT_MEMBER_ID1);
        createMember(project2, PROJECT_MEMBER_ID1);

        List<Project> projectList = projectMemberRepository.findProjectsByMemberId(PROJECT_MEMBER_ID1);
        assertAll(
                () -> assertEquals(2, projectList.size()),
                () -> assertTrue(
                        projectList.stream().anyMatch(
                                project -> project.getAdminMemberId().equals(PROJECT_ADMIN_ID1)
                        )),
                () -> assertTrue(
                        projectList.stream().anyMatch(
                                project -> project.getAdminMemberId().equals(PROJECT_ADMIN_ID2)

                        ))
        );

    }


}