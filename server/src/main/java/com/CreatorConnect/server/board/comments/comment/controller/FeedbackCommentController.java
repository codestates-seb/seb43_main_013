package com.CreatorConnect.server.board.comments.comment.controller;

import com.CreatorConnect.server.board.comments.comment.dto.CommentDto;
import com.CreatorConnect.server.board.comments.comment.dto.CommentResponseDto;
import com.CreatorConnect.server.board.comments.comment.service.FeedbackCommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class FeedbackCommentController {
    private final FeedbackCommentServiceImpl feedbackCommentService;

    @PostMapping("/feedbackboard/{feedbackBoardId}/comment/new")
    public ResponseEntity<CommentResponseDto.CommentContent> postComment(@PathVariable("feedbackBoardId") @Positive Long feedbackBoardId,
                                                                       @Valid @RequestBody CommentDto.Post postDto) {
        CommentResponseDto.CommentContent response = feedbackCommentService.createComment(feedbackBoardId, postDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/feedbackboard/{feedbackBoardId}/comment/{commentId}")
    public ResponseEntity<CommentResponseDto.CommentContent> patchComment(@PathVariable("feedbackBoardId") @Positive Long feedbackBoardId,
                                                                               @PathVariable("commentId") @Positive Long commentId,
                                                                               @Valid @RequestBody CommentDto.Patch patchDto){
        CommentResponseDto.CommentContent response = feedbackCommentService.updateComment(feedbackBoardId, commentId, patchDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/feedbackboard/{feedbackBoardId}/comment/{commentId}")
    public ResponseEntity<CommentResponseDto.Details> getComment(@PathVariable("feedbackBoardId") @Positive Long feedbackBoardId,
                                                                        @PathVariable("commentId") @Positive Long commentId){
        CommentResponseDto.Details response = feedbackCommentService.responseComment(feedbackBoardId, commentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/feedbackboard/{feedbackBoardId}/comments")
    public ResponseEntity getComments(@PathVariable("feedbackBoardId") @Positive Long feedbackBoardId,
                                       @RequestParam("page") @Positive int page,
                                       @RequestParam("size") @Positive int size) {
        CommentResponseDto.Multi response = feedbackCommentService.responseComments(feedbackBoardId, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/feedbackboard/{feedbackBoardId}/comment/{commentId}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("feedbackBoardId") @Positive Long feedbackBoardId,
                                                     @PathVariable("commentId") @Positive Long commentId) {
        feedbackCommentService.deleteComment(feedbackBoardId, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
