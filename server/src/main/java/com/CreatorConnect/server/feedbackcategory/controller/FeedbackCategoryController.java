package com.CreatorConnect.server.feedbackcategory.controller;

import com.CreatorConnect.server.feedbackcategory.dto.FeedbackCategoryDto;
import com.CreatorConnect.server.feedbackcategory.dto.FeedbackCategoryResponseDto;
import com.CreatorConnect.server.feedbackcategory.entity.FeedbackCategory;
import com.CreatorConnect.server.feedbackcategory.mapper.FeedbackCategoryMapper;
import com.CreatorConnect.server.feedbackcategory.service.FeedbackCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

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

    @Secured("ROLE_ADMIN")
    @PostMapping("/feedbackcategory/new")
    public ResponseEntity postFeedbackCategory(@Valid @RequestBody FeedbackCategoryDto.Post feedbackCategoryPost) {
        FeedbackCategory feedbackCategory = mapper.feedbackCategoryPostDtoToFeedbackCategory(feedbackCategoryPost);
        FeedbackCategory createdFeedbackCategory = feedbackCategoryService.createFeedbackCategory(feedbackCategory);

        return new ResponseEntity<>(mapper.feedbackCategoryToFeedbackCategoryResponseDto(createdFeedbackCategory), HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
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

    @GetMapping("/feedbackcategories")
    public ResponseEntity getFeedbackCategorys() {
        List<FeedbackCategoryResponseDto.Details> response = feedbackCategoryService.responseFeedbackCategorys();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/feedbackcategory/{feedbackcategoryId}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable("feedbackCategoryId") @Positive Long feedbackCategoryId) {
        feedbackCategoryService.deleteFeedbackCategory(feedbackCategoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

