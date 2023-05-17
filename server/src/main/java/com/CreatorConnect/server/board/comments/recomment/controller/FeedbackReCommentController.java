package com.CreatorConnect.server.board.comments.recomment.controller;

import com.CreatorConnect.server.board.comments.comment.dto.CommentDto;
import com.CreatorConnect.server.board.comments.recomment.dto.ReCommentDto;
import com.CreatorConnect.server.board.comments.recomment.dto.ReCommentResponseDto;
import com.CreatorConnect.server.board.comments.recomment.service.FeedbackReCommentServiceImpl;
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
public class FeedbackReCommentController {
    private final FeedbackReCommentServiceImpl feedbackReCommentService;

    @PostMapping("/feedbackboard/{feedbackBoardId}/comment/{commentId}/recomment/new")
    public ResponseEntity<ReCommentResponseDto.Post> postReComment(@PathVariable("feedbackBoardId") @Positive Long feedbackBoardId,
                                                                   @PathVariable("commentId") @Positive Long commentId,
                                                                   @Valid @RequestBody ReCommentDto.Post postDto) {
        ReCommentResponseDto.Post response = feedbackReCommentService.createReComment(feedbackBoardId, commentId, postDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/feedbackboard/{feedbackBoardId}/comment/{commentId}/recomment/{reCommentId}")
    public ResponseEntity<HttpStatus> patchComment(@PathVariable("feedbackBoardId") @Positive Long feedbackBoardId,
                                                   @PathVariable("commentId") @Positive Long commentId,
                                                   @PathVariable("reCommentId") @Positive Long reCommentId,
                                                   @Valid @RequestBody CommentDto.Patch patchDto,
                                                   @RequestHeader(value = "Authorization") String authorizationToken){

        String token = authorizationToken.substring(7);
        feedbackReCommentService.updateReComment(token, feedbackBoardId, commentId, reCommentId, patchDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/feedbackboard/{feedbackBoardId}/comment/{commentId}/recomment/{reCommentId}")
    public ResponseEntity<ReCommentResponseDto.Details> getComment(@PathVariable("feedbackBoardId") @Positive Long feedbackBoardId,
                                                                 @PathVariable("commentId") @Positive Long commentId,
                                                                 @PathVariable("reCommentId") @Positive Long reCommentId){
        ReCommentResponseDto.Details response = feedbackReCommentService.responseReComment(feedbackBoardId, commentId, reCommentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/feedbackboard/{feedbackBoardId}/comment/{commentId}/recomment/{reCommentId}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("feedbackBoardId") @Positive Long feedbackBoardId,
                                                    @PathVariable("commentId") @Positive Long commentId,
                                                    @PathVariable("reCommentId") @Positive Long reCommentId,
                                                    @RequestHeader(value = "Authorization") String authorizationToken) {

        String token = authorizationToken.substring(7);
        feedbackReCommentService.deleteReComment(token, feedbackBoardId, commentId, reCommentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
