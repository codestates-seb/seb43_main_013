package com.CreatorConnect.server.board.feedbackboard.controller;

import com.CreatorConnect.server.board.feedbackboard.service.FeedbackBoardService;
import com.CreatorConnect.server.board.feedbackboard.dto.FeedbackBoardDto;
import com.CreatorConnect.server.board.feedbackboard.dto.FeedbackBoardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class FeedbackBoardController {
    private final FeedbackBoardService feedbackBoardService;

    @PostMapping("/feedbackboard/new")
    public ResponseEntity<FeedbackBoardResponseDto.Post> postFeedback(@Valid @RequestBody FeedbackBoardDto.Post postDto,
                                                                      @RequestHeader(value = "Authorization") String authorizationToken) {

        FeedbackBoardResponseDto.Post response = feedbackBoardService.createFeedback(postDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/feedbackboard/{feedbackBoardId}")
    public ResponseEntity<FeedbackBoardResponseDto.Patch> patchFeedback(@PathVariable("feedbackBoardId") Long feedbackBoardId,
                                                                        @Valid @RequestBody FeedbackBoardDto.Patch patchDto,
                                                                        @RequestHeader(value = "Authorization") String authorizationToken){

        FeedbackBoardResponseDto.Patch response = feedbackBoardService.updateFeedback(feedbackBoardId, patchDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/feedbackboard/{feedbackBoardId}")
    public ResponseEntity<FeedbackBoardResponseDto.Details> getFeedback(@PathVariable("feedbackBoardId") @Positive Long feedbackBoardId,
                                                                        HttpServletRequest request){

        FeedbackBoardResponseDto.Details response = feedbackBoardService.responseFeedback(feedbackBoardId, request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/feedbackboards")
    public ResponseEntity getFeedbacks(@RequestParam("sort") String sort,
                                       @RequestParam("page") @Positive int page,
                                       @RequestParam("size") @Positive int size,
                                       HttpServletRequest request) {

        FeedbackBoardResponseDto.Multi response = feedbackBoardService.responseFeedbacks(sort, page, size, request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 목록조회 -> 피드백 카테고리(선택) - 카테고리(전체)
    @GetMapping("/feedbackboards/feedbackcategories/{feedbackCategoryId}")
    public ResponseEntity getFeedbacksByFeedbackCategory(@PathVariable("feedbackCategoryId") @Positive Long feedbackCategoryId,
                                                         @RequestParam("sort") String sort,
                                                         @RequestParam("page") @Positive int page,
                                                         @RequestParam("size") @Positive int size) {

        FeedbackBoardResponseDto.Multi response = feedbackBoardService.responseFeedbacksByCategory(feedbackCategoryId, sort, page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 목록조회 -> 피드백 카테고리(전체) - 카테고리(선택)
    @GetMapping("/feedbackboards/categories/{categoryId}")
    public ResponseEntity getFeedbacksByCategory(@PathVariable("categoryId") @Positive Long categoryId,
                                                 @RequestParam("sort") String sort,
                                                 @RequestParam("page") @Positive int page,
                                                 @RequestParam("size") @Positive int size) {

        FeedbackBoardResponseDto.Multi response = feedbackBoardService.responseFeedbacksByFeedbackCategory(categoryId, sort, page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 목록조회 -> 피드백 카테고리(선택) - 카테고리(선택)
    @GetMapping("/feedbackboards/feedbackcategories/{feedbackCategoryId}/categories/{categoryId}")
    public ResponseEntity getFeedbacksByFeedbackCategoryAndCategory(@PathVariable("feedbackCategoryId") @Positive Long feedbackCategoryId,
                                                         @PathVariable("categoryId") @Positive Long categoryId,
                                                         @RequestParam("sort") String sort,
                                                         @RequestParam("page") @Positive int page,
                                                         @RequestParam("size") @Positive int size) {

        FeedbackBoardResponseDto.Multi response = feedbackBoardService.responseFeedbacksByCategory(feedbackCategoryId, categoryId, sort, page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/feedbackboard/{feedbackBoardId}")
    public ResponseEntity<HttpStatus> deleteFeedback(@PathVariable("feedbackBoardId") @Positive Long feedbackBoardId,
                                                     @RequestHeader(value = "Authorization") String authorizationToken) {

        feedbackBoardService.deleteFeedback(feedbackBoardId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/feedbackboard/{feedbackBoardId}/like")
    public ResponseEntity likeFeedbackBoard (@PathVariable("feedbackBoardId") @Positive Long feedbackBoardId,
                                             @RequestHeader(value = "Authorization") String authorizationToken) {

        feedbackBoardService.likeFeedbackBoard(feedbackBoardId, authorizationToken);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/feedbackboard/{feedbackBoardId}/like")
    public ResponseEntity unlikeFeedbackBoard (@PathVariable("feedbackBoardId") @Positive Long feedbackBoardId,
                                               @RequestHeader(value = "Authorization") String authorizationToken) {

        feedbackBoardService.unlike(feedbackBoardId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/feedbackboard/{feedbackBoardId}/bookmark")
    public ResponseEntity bookmarkFeedbackBoard (@PathVariable("feedbackBoardId") @Positive Long feedbackBoardId,
                                                 @RequestHeader(value = "Authorization") String authorizationToken) {

        feedbackBoardService.bookmarkFeedbackBoard(feedbackBoardId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/feedbackboard/{feedbackBoardId}/bookmark")
    public ResponseEntity unbookmarkFeedbackBoard (@PathVariable("feedbackBoardId") @Positive Long feedbackBoardId,
                                                   @RequestHeader(value = "Authorization") String authorizationToken) {

        feedbackBoardService.unbookmarkFeedbackBoard(feedbackBoardId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
