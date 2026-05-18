package com.nhnacademy.team4.taskapi.project.web;


import com.nhnacademy.team4.taskapi.project.application.command.AddProjectMemberCommand;
import com.nhnacademy.team4.taskapi.project.application.command.CreateProjectCommand;
import com.nhnacademy.team4.taskapi.project.application.command.UpdateProjectCommand;
import com.nhnacademy.team4.taskapi.project.application.result.ProjectSummaryResult;
import com.nhnacademy.team4.taskapi.project.application.usecase.AddProjectMemberUseCase;
import com.nhnacademy.team4.taskapi.project.application.usecase.CreateProjectUseCase;
import com.nhnacademy.team4.taskapi.project.application.usecase.GetMyProjectUseCase;
import com.nhnacademy.team4.taskapi.project.application.usecase.UpdateProjectUseCase;
import com.nhnacademy.team4.taskapi.project.web.request.CreateProjectRequest;
import com.nhnacademy.team4.taskapi.project.web.request.UpdateProjectRequest;
import com.nhnacademy.team4.taskapi.project.web.response.ProjectSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {
    private final CreateProjectUseCase createProjectUseCase;
    private final AddProjectMemberUseCase addProjectMemberUseCase;
    private final UpdateProjectUseCase updateProjectUseCase;
    private final GetMyProjectUseCase getMyProjectUseCase;

    @PostMapping
    public ResponseEntity<ProjectSummaryResponse> createProject(
            @RequestHeader("X-MEMBER-ID") Long writerMemberId,
            @RequestBody CreateProjectRequest request
    ) {
        CreateProjectCommand command = request.toCreateProjectCommand(writerMemberId);

        ProjectSummaryResult result = createProjectUseCase.createProject(command);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ProjectSummaryResponse.from(result));

    }

    @PutMapping("/{projectId}/members/{memberId}")
    public ResponseEntity<Void> addProjectMember(
            @RequestHeader("X-MEMBER-ID") Long writerMemberId,
            @PathVariable Long projectId,
            @PathVariable Long memberId
    ) {
        AddProjectMemberCommand command = AddProjectMemberCommand.create(projectId, memberId, writerMemberId);

        addProjectMemberUseCase.addProjectMember(command);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<ProjectSummaryResponse> updateProject(
            @RequestHeader("X-MEMBER-ID") Long writerMemberId,
            @PathVariable Long projectId,
            @RequestBody UpdateProjectRequest request
    ) {
        UpdateProjectCommand command = request.toUpdateProjectCommand(projectId, writerMemberId);

        ProjectSummaryResult result = updateProjectUseCase.updateProject(command);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ProjectSummaryResponse.from(result));
    }

    @GetMapping
    public ResponseEntity<List<ProjectSummaryResponse>> findMyProjects(
            @RequestHeader("X-MEMBER-ID") Long writerMemberId
    )
    {
        List<ProjectSummaryResult> myProjects =
                getMyProjectUseCase.getMyProjects(writerMemberId);

        List<ProjectSummaryResponse> responses = myProjects.stream()
                .map(ProjectSummaryResponse::from)
                .toList();

        return ResponseEntity.ok(responses);
    }
}
