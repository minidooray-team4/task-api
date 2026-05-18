package com.nhnacademy.team4.taskapi.tags.web;

import com.nhnacademy.team4.taskapi.tags.application.command.AttachTagToTaskCommand;
import com.nhnacademy.team4.taskapi.tags.application.command.CreateTagCommand;
import com.nhnacademy.team4.taskapi.tags.application.command.DetachTagFromTaskCommand;
import com.nhnacademy.team4.taskapi.tags.application.usecase.*;
import com.nhnacademy.team4.taskapi.tags.web.request.CreateTagRequest;
import com.nhnacademy.team4.taskapi.tags.web.request.UpdateTagRequest;
import com.nhnacademy.team4.taskapi.tags.web.response.TagResponse;
import com.nhnacademy.team4.taskapi.task.application.result.TagResult;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController //@Controller + @ResponseBody 합친놈, 반환값을 JSON으로 변환
@RequestMapping("/api")
public class TagController {

    private final CreateTagUseCase createTagUseCase;
    private final DeleteTagUseCase deleteTagUseCase;
    private final UpdateTagUseCase updateTagUseCase;
    private final GetProjectTagsUseCase getProjectTagsUseCase;
    private final AttachTagToTaskUseCase attachTagToTaskUseCase;
    private final DetachTagFromTaskUseCase detachTagFromTaskUseCase;

    @PostMapping("/projects/{projectId}/tags")
    public ResponseEntity<TagResponse> addTag(
            @PathVariable Long projectId,
            @RequestHeader("X-MEMBER-ID") Long requesterMemberId,
            @RequestBody CreateTagRequest request
    ){
        CreateTagCommand command=request.toCreateTagCommand(projectId,requesterMemberId);
        TagResult tagResult=createTagUseCase.createTag(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(TagResponse.from(tagResult));
    }

    @GetMapping("/projects/{projectId}/tags")
    public ResponseEntity<List<TagResponse>>getTags(
            @PathVariable Long projectId,
            @RequestHeader("X-MEMBER-ID") Long requesterMemberId
    ){
        List<TagResult> tagResults=getProjectTagsUseCase.getProjectTags(projectId,requesterMemberId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tagResults.stream().map(TagResponse::from).toList());
    }

    @PatchMapping("/tags/{tagId}")
    public ResponseEntity<TagResponse> updateTag(
            @PathVariable Long tagId,
            @RequestHeader("X-MEMBER-ID") Long requesterMemberId,
            @RequestBody UpdateTagRequest request
    ){
        TagResult tagResult=updateTagUseCase.updateTag(request.toUpdateTagCommand(tagId,requesterMemberId));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(TagResponse.from(tagResult));
    }

    @DeleteMapping("/tags/{tagId}")
    public ResponseEntity<Void> deleteTag(
            @PathVariable Long tagId,
            @RequestHeader("X-MEMBER-ID") Long requesterMemberId

    ){
        deleteTagUseCase.deleteTag(tagId,requesterMemberId);
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
        attachTagToTaskUseCase.attachTagToTask(new AttachTagToTaskCommand(taskId,tagId,requesterMemberId));
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
        detachTagFromTaskUseCase.detachTagFromTask(new DetachTagFromTaskCommand(taskId,tagId,requesterMemberId));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
