package com.CreatorConnect.server.board.comments.recomment.controller;

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
}
