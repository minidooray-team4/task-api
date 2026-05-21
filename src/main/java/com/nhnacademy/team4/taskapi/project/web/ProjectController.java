package com.nhnacademy.team4.taskapi.project.web;


import com.nhnacademy.team4.taskapi.project.application.command.AddProjectMemberCommand;
import com.nhnacademy.team4.taskapi.project.application.command.CreateProjectCommand;
import com.nhnacademy.team4.taskapi.project.application.command.UpdateProjectCommand;
import com.nhnacademy.team4.taskapi.project.application.ProjectCommandService;
import com.nhnacademy.team4.taskapi.project.application.ProjectQueryService;
import com.nhnacademy.team4.taskapi.project.application.result.ProjectDetailResult;
import com.nhnacademy.team4.taskapi.project.application.result.ProjectMemberResult;
import com.nhnacademy.team4.taskapi.project.application.result.ProjectSummaryResult;
import com.nhnacademy.team4.taskapi.project.web.request.CreateProjectRequest;
import com.nhnacademy.team4.taskapi.project.web.request.UpdateProjectRequest;
import com.nhnacademy.team4.taskapi.project.web.response.CreatedProjectResponse;
import com.nhnacademy.team4.taskapi.project.web.response.ProjectDetailResponse;
import com.nhnacademy.team4.taskapi.project.web.response.ProjectMemberResponse;
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
    private final ProjectCommandService projectCommandService;
    private final ProjectQueryService projectQueryService;

    @PostMapping
    public ResponseEntity<CreatedProjectResponse> createProject(
            @RequestHeader("X-MEMBER-ID") Long writerMemberId,
            @RequestBody CreateProjectRequest request
    ) {
        CreateProjectCommand command = request.toCreateProjectCommand(writerMemberId);

        Long projectId = projectCommandService.createProject(command);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CreatedProjectResponse(projectId));

    }

    @PutMapping("/{projectId}/members/{memberId}")
    public ResponseEntity<Void> addProjectMember(
            @RequestHeader("X-MEMBER-ID") Long writerMemberId,
            @PathVariable Long projectId,
            @PathVariable Long memberId
    ) {
        AddProjectMemberCommand command = AddProjectMemberCommand.create(projectId, memberId, writerMemberId);

        projectCommandService.addProjectMember(command);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<Void> updateProject(
            @RequestHeader("X-MEMBER-ID") Long writerMemberId,
            @PathVariable Long projectId,
            @RequestBody UpdateProjectRequest request
    ) {
        UpdateProjectCommand command = request.toUpdateProjectCommand(projectId, writerMemberId);

        projectCommandService.updateProject(command);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDetailResponse> getProjectDetail(
            @RequestHeader("X-MEMBER-ID") Long writerMemberId,
            @PathVariable Long projectId
    ) {
        ProjectDetailResult result = projectQueryService.getProjectDetail(projectId, writerMemberId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ProjectDetailResponse.from(result));
    }

    @GetMapping
    public ResponseEntity<List<ProjectSummaryResponse>> findMyProjects(
            @RequestHeader("X-MEMBER-ID") Long writerMemberId
    ) {
        List<ProjectSummaryResult> myProjects =
                projectQueryService.getMyProjects(writerMemberId);

        List<ProjectSummaryResponse> responses = myProjects.stream()
                .map(ProjectSummaryResponse::from)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{projectId}/members")
    public ResponseEntity<List<ProjectMemberResponse>> getProjectMembers(
            @RequestHeader("X-MEMBER-ID") Long writerMemberId,
            @PathVariable Long projectId
    ) {
        List<ProjectMemberResult> projectMembers = projectQueryService.getProjectMembers(projectId, writerMemberId);

        List<ProjectMemberResponse> responses = projectMembers.stream()
                .map(ProjectMemberResponse::from)
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responses);

    }
}
