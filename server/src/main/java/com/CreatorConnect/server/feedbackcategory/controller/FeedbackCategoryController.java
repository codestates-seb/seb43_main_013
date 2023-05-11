package com.CreatorConnect.server.feedbackcategory.controller;

import com.CreatorConnect.server.feedbackboard.dto.FeedbackBoardResponseDto;
import com.CreatorConnect.server.feedbackcategory.dto.FeedbackCategoryDto;
import com.CreatorConnect.server.feedbackcategory.dto.FeedbackCategoryResponseDto;
import com.CreatorConnect.server.feedbackcategory.entity.FeedbackCategory;
import com.CreatorConnect.server.feedbackcategory.mapper.FeedbackCategoryMapper;
import com.CreatorConnect.server.feedbackcategory.service.FeedbackCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api")
@Validated
public class FeedbackCategoryController {
    private final FeedbackCategoryService feedbackCategoryService;
    private final FeedbackCategoryMapper mapper;

    public FeedbackCategoryController(FeedbackCategoryService feedbackCategoryService, FeedbackCategoryMapper mapper) {
        this.feedbackCategoryService = feedbackCategoryService;
        this.mapper = mapper;
    }

    @PostMapping("/feedbackcategory/new")
    public ResponseEntity postFeedbackCategory(@Valid @RequestBody FeedbackCategoryDto.Post feedbackCategoryPost) {
        FeedbackCategory feedbackCategory = mapper.feedbackCategoryPostDtoToFeedbackCategory(feedbackCategoryPost);
        FeedbackCategory createdFeedbackCategory = feedbackCategoryService.createFeedbackCategory(feedbackCategory);

        return new ResponseEntity<>(mapper.feedbackCategoryToFeedbackCategoryResponseDto(createdFeedbackCategory), HttpStatus.CREATED);
    }

    @PatchMapping("/feedbackcategory/{feedbackCategoryId}")
    public ResponseEntity<FeedbackCategoryResponseDto.Patch> patchFeedbackCategory(@PathVariable("feedbackCategoryId") Long feedbackCategoryId,
                                                                           @Valid @RequestBody FeedbackCategoryDto.Patch patchDto){
        FeedbackCategoryResponseDto.Patch response = feedbackCategoryService.updateFeedbackCategory(feedbackCategoryId, patchDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/feedbackcategory/{feedbackCategoryId}")
    public ResponseEntity<FeedbackCategoryResponseDto.Details> getFeedbackCategory(@PathVariable("feedbackCategoryId") @Positive Long feedbackCategoryId){
        FeedbackCategoryResponseDto.Details response = feedbackCategoryService.responseFeedbackCategory(feedbackCategoryId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/feedbackcategorys")
    public ResponseEntity getFeedbackCategorys(@RequestParam("page") @Positive int page,
                                       @RequestParam("size") @Positive int size) {
        FeedbackCategoryResponseDto.Multi response = feedbackCategoryService.responseFeedbackCategorys(page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/feedbackcategory/{feedbackBoardId}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable("feedbackCategoryId") @Positive Long feedbackCategoryId) {
        feedbackCategoryService.deleteFeedbackCategory(feedbackCategoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

