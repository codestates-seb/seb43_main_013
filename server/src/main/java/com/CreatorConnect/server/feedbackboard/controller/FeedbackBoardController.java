package com.CreatorConnect.server.feedbackboard.controller;

import com.CreatorConnect.server.feedbackboard.dto.FeedbackBoardDto;
import com.CreatorConnect.server.feedbackboard.dto.FeedbackBoardResponseDto;
import com.CreatorConnect.server.feedbackboard.service.FeedbackBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@RestController
@RequestMapping("/api/feedbackboard")
@RequiredArgsConstructor
@Validated
public class FeedbackBoardController {
    private final FeedbackBoardService feedbackBoardService;

    @PostMapping("/new")
    public ResponseEntity<FeedbackBoardResponseDto.Post> postFeedback(@Valid @RequestBody FeedbackBoardDto.Post postDto) {
        FeedbackBoardResponseDto.Post response = feedbackBoardService.createFeedback(postDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{feedbackBoard-id}")
    public ResponseEntity<FeedbackBoardResponseDto.Patch> patchFeedback(@PathVariable("feedbackBoard-id") Long feedbackBoardId,
                                                                        @Valid @RequestBody FeedbackBoardDto.Patch patchDto){
        FeedbackBoardResponseDto.Patch response = feedbackBoardService.updateFeedback(feedbackBoardId, patchDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
