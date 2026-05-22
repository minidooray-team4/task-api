package com.nhnacademy.team4.taskapi.tags.web;


import com.nhnacademy.team4.taskapi.tags.application.TagCommandService;
import com.nhnacademy.team4.taskapi.tags.application.TagQueryService;
import com.nhnacademy.team4.taskapi.tags.application.command.AttachTagToTaskCommand;
import com.nhnacademy.team4.taskapi.tags.application.command.DetachTagFromTaskCommand;
import com.nhnacademy.team4.taskapi.tags.application.result.TagResult;
import com.nhnacademy.team4.taskapi.tags.web.request.CreateTagRequest;
import com.nhnacademy.team4.taskapi.tags.web.request.UpdateTagRequest;
import com.nhnacademy.team4.taskapi.tags.web.response.TagResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController //@Controller + @ResponseBody 합친놈, 반환값을 JSON으로 변환
@RequestMapping("/api")
public class TagController {
    private final TagCommandService tagCommandService;
    private final TagQueryService tagQueryService;

    @PostMapping("/projects/{projectId}/tags")
    public ResponseEntity<Void> addTag(
            @PathVariable Long projectId,
            @RequestHeader("X-MEMBER-ID") Long requesterMemberId,
            @RequestBody CreateTagRequest request
    ){
        tagCommandService.createTag(request.toCreateTagCommand(projectId,requesterMemberId));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/projects/{projectId}/tags")
    public ResponseEntity<List<TagResponse>>getTags(
            @PathVariable Long projectId,
            @RequestHeader("X-MEMBER-ID") Long requesterMemberId
    ){
        List<TagResult> tagResults=tagQueryService.getProjectTags(projectId,requesterMemberId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tagResults.stream().map(TagResponse::from).toList());
    }

    @PatchMapping("/tags/{tagId}")
    public ResponseEntity<Void> updateTag(
            @PathVariable Long tagId,
            @RequestHeader("X-MEMBER-ID") Long requesterMemberId,
            @RequestBody UpdateTagRequest request
    ){
        tagCommandService.updateTag(request.toUpdateTagCommand(tagId,requesterMemberId));

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/tags/{tagId}")
    public ResponseEntity<Void> deleteTag(
            @PathVariable Long tagId,
            @RequestHeader("X-MEMBER-ID") Long requesterMemberId

    ){
        tagCommandService.deleteTag(tagId,requesterMemberId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/tasks/{taskId}/tags/{tagId}")
    public ResponseEntity<Void> attachTagToTask(
            @PathVariable Long taskId,
            @PathVariable Long tagId,
            @RequestHeader("X-MEMBER-ID") Long requesterMemberId
    ){
        tagCommandService.attachTagToTask(new AttachTagToTaskCommand(taskId,tagId,requesterMemberId));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/tasks/{taskId}/tags/{tagId}")
    public ResponseEntity<Void> detachTagFromTask(
            @PathVariable Long taskId,
            @PathVariable Long tagId,
            @RequestHeader("X-MEMBER-ID") Long requesterMemberId
    ){
        tagCommandService.detachTagFromTask(new DetachTagFromTaskCommand(taskId,tagId,requesterMemberId));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
