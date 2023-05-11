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
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class FeedbackBoardController {
    private final FeedbackBoardService feedbackBoardService;
    @PostMapping("/feedbackboard/new")
    public ResponseEntity<FeedbackBoardResponseDto.Post> postFeedback(@Valid @RequestBody FeedbackBoardDto.Post postDto) {
        FeedbackBoardResponseDto.Post response = feedbackBoardService.createFeedback(postDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PatchMapping("/feedbackboard/{feedbackBoardId}")
    public ResponseEntity<FeedbackBoardResponseDto.Patch> patchFeedback(@PathVariable("feedbackBoardId") Long feedbackBoardId,
                                                                        @Valid @RequestBody FeedbackBoardDto.Patch patchDto){
        FeedbackBoardResponseDto.Patch response = feedbackBoardService.updateFeedback(feedbackBoardId, patchDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/feedbackboard/{feedbackBoardId}")
    public ResponseEntity<FeedbackBoardResponseDto.Details> getFeedback(@PathVariable("feedbackBoardId") @Positive Long feedbackBoardId){
        FeedbackBoardResponseDto.Details response = feedbackBoardService.responseFeedback(feedbackBoardId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/feedbackboards")
    public ResponseEntity getFeedbacks(@RequestParam("page") @Positive int page,
                                       @RequestParam("size") @Positive int size) {
        FeedbackBoardResponseDto.Multi response = feedbackBoardService.responseFeedbacks(page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/feedbackboards/category/{feedbackCategoryId}")
    public ResponseEntity getFeedbacksByFeedbackCategory(@PathVariable("feedbackCategoryId") @Positive Long feedbackCategoryId,
                                                 @RequestParam("page") @Positive int page,
                                                 @RequestParam("size") @Positive int size) {
        FeedbackBoardResponseDto.Multi response = feedbackBoardService.responseFeedbacksByCategory(feedbackCategoryId, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/feedbackboard/{feedbackBoardId}")
    public ResponseEntity<HttpStatus> deleteFeedback(@PathVariable("feedbackBoardId") @Positive Long feedbackBoardId) {
        feedbackBoardService.deleteFeedback(feedbackBoardId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
