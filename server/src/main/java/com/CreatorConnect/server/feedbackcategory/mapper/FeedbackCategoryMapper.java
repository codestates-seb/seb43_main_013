package com.CreatorConnect.server.feedbackcategory.mapper;

import com.CreatorConnect.server.feedbackboard.dto.FeedbackBoardDto;
import com.CreatorConnect.server.feedbackboard.dto.FeedbackBoardResponseDto;
import com.CreatorConnect.server.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.feedbackcategory.dto.FeedbackCategoryDto;
import com.CreatorConnect.server.feedbackcategory.dto.FeedbackCategoryResponseDto;
import com.CreatorConnect.server.feedbackcategory.entity.FeedbackCategory;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FeedbackCategoryMapper {
    // CategoryDto.Post -> Category
    FeedbackCategory feedbackCategoryPostDtoToFeedbackCategory(FeedbackCategoryDto.Post post);

    // Category -> CategoryDto.Response
    FeedbackCategoryResponseDto.Post feedbackCategoryToFeedbackCategoryResponseDto(FeedbackCategory feedbackCategory);

    FeedbackCategory feedbackCategoryPatchDtoToFeedbackCategory(FeedbackCategoryDto.Patch feedbackCategoryPatchDto);
    FeedbackCategoryResponseDto.Patch feedbackCategoryToFeedbackCategoryPatchResponse(FeedbackCategory feedbackCategory);
    FeedbackCategoryResponseDto.Details feedbackCategoryToFeedbackCategoryDetailsResponse(FeedbackCategory feedbackCategory);
    List<FeedbackCategoryResponseDto.Details> feedbackCategorysToFeedbackCategoryDetailsResponses (List<FeedbackCategory> feedbackCategorys);
}

