package com.CreatorConnect.server.feedbackcategory.service;

import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import com.CreatorConnect.server.feedbackcategory.dto.FeedbackCategoryDto;
import com.CreatorConnect.server.feedbackcategory.dto.FeedbackCategoryResponseDto;
import com.CreatorConnect.server.feedbackcategory.entity.FeedbackCategory;
import com.CreatorConnect.server.feedbackcategory.mapper.FeedbackCategoryMapper;
import com.CreatorConnect.server.feedbackcategory.repository.FeedbackCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Transactional
@Service
public class FeedbackCategoryService {
    private final FeedbackCategoryRepository feedbackCategoryRepository;

    private final FeedbackCategoryMapper mapper;

    /**
     * <피드백 카테고리 기능>
     * 1. 등록
     * 2. 패치
     * 3. 삭제
     * 4. 조회
     * 5. 목록 조회
     * 6. 카테고리 중복 검사
     */
    public FeedbackCategory createFeedbackCategory(FeedbackCategory feedbackCategory) {
        // 1. 카테고리 중복 검사
        verifyFeedbackCategory(feedbackCategory.getFeedbackCategoryName());

        // 2. 등록
        return feedbackCategoryRepository.save(feedbackCategory);
    }

    // 패치
    public FeedbackCategoryResponseDto.Patch updateFeedbackCategory(Long feedbackCategoryId, FeedbackCategoryDto.Patch patchDto){
        FeedbackCategory feedbackCategory = mapper.feedbackCategoryPatchDtoToFeedbackCategory(patchDto);
        feedbackCategory.setFeedbackCategoryId(feedbackCategoryId);
        FeedbackCategory foundFeedbackCategory = findVerifiedFeedbackCategory(feedbackCategory.getFeedbackCategoryId());

        Optional.ofNullable(feedbackCategory.getFeedbackCategoryName())
                .ifPresent(foundFeedbackCategory::setFeedbackCategoryName);

        FeedbackCategory updatedFeedbackCategory = feedbackCategoryRepository.save(foundFeedbackCategory);
        FeedbackCategoryResponseDto.Patch responseDto = mapper.feedbackCategoryToFeedbackCategoryPatchResponse(updatedFeedbackCategory);
        return responseDto;
    }

    //조회
    public FeedbackCategoryResponseDto.Details responseFeedbackCategory(Long FeedbackCategoryId){
        FeedbackCategory foundFeedbackCategory = findVerifiedFeedbackCategory(FeedbackCategoryId);
        return mapper.feedbackCategoryToFeedbackCategoryDetailsResponse(foundFeedbackCategory);
    }

    //목록 조회
    public List<FeedbackCategoryResponseDto.Details> responseFeedbackCategorys(){
        PageRequest pageRequest = PageRequest.of(0, 100, Sort.by("feedbackCategoryId").descending());
        Page<FeedbackCategory> feedbackCategorysPage = feedbackCategoryRepository.findAll(pageRequest);
        List<FeedbackCategoryResponseDto.Details> responses = mapper.feedbackCategorysToFeedbackCategoryDetailsResponses(feedbackCategorysPage.getContent());
        return responses;
    }

    //삭제
    public void deleteFeedbackCategory(Long feedbackCategoryId) {
        FeedbackCategory feedbackCategory = findVerifiedFeedbackCategory(feedbackCategoryId);
        feedbackCategoryRepository.delete(feedbackCategory);
    }

    // 피드백 카테고리 이름 중복 검사 메서드
    private void verifyFeedbackCategory(String feedbackCategoryName) {
        Optional<FeedbackCategory> optionalFeedbackCategory = feedbackCategoryRepository.findByFeedbackCategoryName(feedbackCategoryName);
        if (optionalFeedbackCategory.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.CATEGORY_EXISTS);
        }
    }

    // 피드백 카테고리 조회 메서드
    public FeedbackCategory findVerifiedFeedbackCategory(Long feedbackCategoryId){
        Optional<FeedbackCategory> optionalFeedbackCategory = feedbackCategoryRepository.findById(feedbackCategoryId);
        FeedbackCategory feedbackCategory = optionalFeedbackCategory.orElseThrow(() -> new BusinessLogicException(ExceptionCode.FEEDBACK_CATEGORY_NOT_FOUND));
        return  feedbackCategory;
    }
}

