package com.CreatorConnect.server.comment.controller;


import com.CreatorConnect.server.comment.dto.CommentDto;
import com.CreatorConnect.server.comment.dto.CommentResponseDto;
import com.CreatorConnect.server.comment.service.CommentService;
import com.CreatorConnect.server.feedbackboard.dto.FeedbackBoardDto;
import com.CreatorConnect.server.feedbackboard.dto.FeedbackBoardResponseDto;
import com.CreatorConnect.server.feedbackboard.service.FeedbackBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/feedbackboard/{feedbackboard-id}/comment/new}")
    public ResponseEntity<CommentResponseDto.Post> postFeedbackComment(@Valid @RequestBody CommentDto.Post postDto) {
        CommentResponseDto.Post response = commentService.createComment(postDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
