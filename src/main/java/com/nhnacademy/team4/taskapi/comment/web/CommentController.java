package com.nhnacademy.team4.taskapi.comment.web;

import com.nhnacademy.team4.taskapi.comment.application.CommentCommandService;
import com.nhnacademy.team4.taskapi.comment.application.CommentQueryService;
import com.nhnacademy.team4.taskapi.comment.application.command.CreateCommentCommand;
import com.nhnacademy.team4.taskapi.comment.application.command.UpdateCommentCommand;
import com.nhnacademy.team4.taskapi.comment.application.result.CommentResult;
import com.nhnacademy.team4.taskapi.comment.web.request.CreateCommentRequest;
import com.nhnacademy.team4.taskapi.comment.web.request.UpdateCommentRequest;
import com.nhnacademy.team4.taskapi.comment.web.response.CommentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentQueryService commentQueryService;
    private final CommentCommandService commentCommandService;

    @PostMapping("/tasks/{taskId}/comments")
    public ResponseEntity<Void> createComment(
            @RequestHeader("X-MEMBER-ID") Long requesterMemberId,
            @PathVariable Long taskId,
            @Valid @RequestBody CreateCommentRequest request
    ) {
        CreateCommentCommand command = request.toCommand(taskId, requesterMemberId);

        commentCommandService.createComment(command);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();

    }

    @GetMapping("/tasks/{taskId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(
            @RequestHeader("X-MEMBER-ID") Long requesterMemberId,
            @PathVariable Long taskId
    ) {
        List<CommentResult> results = commentQueryService.getComments(taskId, requesterMemberId);

        List<CommentResponse> responses = results.stream()
                .map(CommentResponse::from)
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responses);

    }


    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<Void> updateComment(
            @RequestHeader("X-MEMBER-ID") Long requesterMemberId,
            @PathVariable Long commentId,
            @Valid @RequestBody UpdateCommentRequest request
    ) {
        UpdateCommentCommand command = request.toCommand(commentId, requesterMemberId);
        commentCommandService.updateComment(command);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @RequestHeader("X-MEMBER-ID") Long requesterMemberId,
            @PathVariable Long commentId
    ) {
        commentCommandService.deleteComment(commentId, requesterMemberId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

    }


}
