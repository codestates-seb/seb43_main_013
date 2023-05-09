package com.CreatorConnect.server.feedbackcategory.mapper;

import com.CreatorConnect.server.feedbackcategory.dto.FeedbackCategoryDto;
import com.CreatorConnect.server.feedbackcategory.entity.FeedbackCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeedbackCategoryMapper {
    // CategoryDto.Post -> Category
    FeedbackCategory feedbackCategoryPostDtoToFeedbackCategory(FeedbackCategoryDto.Post post);

    // Category -> CategoryDto.Response
    FeedbackCategoryDto.Response feedbackCategoryToFeedbackCategoryResponseDto(FeedbackCategory feedbackCategory);
}

