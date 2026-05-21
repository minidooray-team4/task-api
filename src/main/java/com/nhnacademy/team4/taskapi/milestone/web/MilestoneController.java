package com.nhnacademy.team4.taskapi.milestone.web;

import com.nhnacademy.team4.taskapi.milestone.application.command.CreateMilestoneCommand;
import com.nhnacademy.team4.taskapi.milestone.application.command.UpdateMilestoneCommand;
import com.nhnacademy.team4.taskapi.milestone.application.MilestoneCommandService;
import com.nhnacademy.team4.taskapi.milestone.application.MilestoneQueryService;
import com.nhnacademy.team4.taskapi.milestone.application.result.MilestoneResult;
import com.nhnacademy.team4.taskapi.milestone.web.request.CreateMilestoneRequest;

import com.nhnacademy.team4.taskapi.milestone.web.request.UpdateMilestoneRequest;
import com.nhnacademy.team4.taskapi.milestone.web.response.MilestoneResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MilestoneController {

    private final MilestoneCommandService milestoneCommandService;
    private final MilestoneQueryService milestoneQueryService;

    @PostMapping("/projects/{projectId}/milestones")
    public ResponseEntity<Void> createMilestone(
            @RequestHeader("X-MEMBER-ID") Long requesterMemberId,
            @PathVariable Long projectId,
            @RequestBody CreateMilestoneRequest request
    ) {
        CreateMilestoneCommand command = request.toCommand(projectId, requesterMemberId);
        milestoneCommandService.createMilestone(command);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/projects/{projectId}/milestones")
    public ResponseEntity<List<MilestoneResponse>> getProjectMilestones(
            @RequestHeader("X-MEMBER-ID") Long requesterMemberId,
            @PathVariable Long projectId
    ) {
        List<MilestoneResult> results = milestoneQueryService.getProjectMilestones(projectId, requesterMemberId);

        List<MilestoneResponse> responses = results.stream()
                .map(MilestoneResponse::from)
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responses);

    }

    @PatchMapping("/milestones/{milestoneId}")
    public ResponseEntity<Void> updateMilestone(
            @RequestHeader("X-MEMBER-ID") Long requesterMemberId,
            @PathVariable Long milestoneId,
            @RequestBody UpdateMilestoneRequest request
    ) {
        UpdateMilestoneCommand command = request.toCommand(milestoneId, requesterMemberId);
        milestoneCommandService.updateMilestone(command);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/milestones/{milestoneId}")
    public ResponseEntity<Void> deleteMilestone(
            @RequestHeader("X-MEMBER-ID") Long requesterMemberId,
            @PathVariable Long milestoneId
    ) {
        milestoneCommandService.deleteMilestone(milestoneId,requesterMemberId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }


}
