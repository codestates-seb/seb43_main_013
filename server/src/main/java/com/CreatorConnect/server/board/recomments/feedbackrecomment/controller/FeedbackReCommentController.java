package com.CreatorConnect.server.board.recomments.feedbackrecomment.controller;

import com.CreatorConnect.server.board.comments.common.dto.CommentDto;
import com.CreatorConnect.server.board.recomments.common.dto.ReCommentDto;
import com.CreatorConnect.server.board.recomments.common.dto.ReCommentResponseDto;
import com.CreatorConnect.server.board.recomments.feedbackrecomment.service.FeedbackReCommentServiceImpl;
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
                                                                   @Valid @RequestBody ReCommentDto.Post postDto,
                                                                   @RequestHeader(value = "Authorization") String authorizationToken) {

        ReCommentResponseDto.Post response = feedbackReCommentService.createReComment(feedbackBoardId, commentId, postDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/feedbackboard/{feedbackBoardId}/comment/{commentId}/recomment/{reCommentId}")
    public ResponseEntity<HttpStatus> patchComment(@PathVariable("feedbackBoardId") @Positive Long feedbackBoardId,
                                                   @PathVariable("commentId") @Positive Long commentId,
                                                   @PathVariable("reCommentId") @Positive Long reCommentId,
                                                   @Valid @RequestBody CommentDto.Patch patchDto,
                                                   @RequestHeader(value = "Authorization") String authorizationToken){

        feedbackReCommentService.updateReComment(feedbackBoardId, commentId, reCommentId, patchDto);

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

        feedbackReCommentService.deleteReComment(feedbackBoardId, commentId, reCommentId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}