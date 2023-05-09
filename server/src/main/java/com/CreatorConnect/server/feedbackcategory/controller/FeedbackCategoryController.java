package com.CreatorConnect.server.feedbackcategory.controller;

import com.CreatorConnect.server.feedbackcategory.dto.FeedbackCategoryDto;
import com.CreatorConnect.server.feedbackcategory.entity.FeedbackCategory;
import com.CreatorConnect.server.feedbackcategory.mapper.FeedbackCategoryMapper;
import com.CreatorConnect.server.feedbackcategory.service.FeedbackCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/FeedbackCategory")
@Validated
public class FeedbackCategoryController {
    private final FeedbackCategoryService feedbackCategoryService;
    private final FeedbackCategoryMapper mapper;

    public FeedbackCategoryController(FeedbackCategoryService feedbackCategoryService, FeedbackCategoryMapper mapper) {
        this.feedbackCategoryService = feedbackCategoryService;
        this.mapper = mapper;
    }

    @PostMapping("/new")
    public ResponseEntity postFeedbackCategory(@Valid @RequestBody FeedbackCategoryDto.Post feedbackCategoryPost) {
        FeedbackCategory feedbackCategory = mapper.feedbackCategoryPostDtoToFeedbackCategory(feedbackCategoryPost);
        FeedbackCategory createdFeedbackCategory = feedbackCategoryService.createFeedbackCategory(feedbackCategory);

        return new ResponseEntity<>(mapper.feedbackCategoryToFeedbackCategoryResponseDto(createdFeedbackCategory), HttpStatus.CREATED);
    }
}

