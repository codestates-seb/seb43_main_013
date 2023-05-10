package com.CreatorConnect.server.feedbackcategory.service;

import com.CreatorConnect.server.exception.BusinessLogicException;
import com.CreatorConnect.server.exception.ExceptionCode;
import com.CreatorConnect.server.feedbackcategory.entity.FeedbackCategory;
import com.CreatorConnect.server.feedbackcategory.repository.FeedbackCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@RequiredArgsConstructor
@Transactional
@Service
public class FeedbackCategoryService {
    private final FeedbackCategoryRepository feedbackCategoryRepository;

    /**
     * <카테고리 등록 - 임시 기능>
     * 1. 카테고리 중복 검사
     * 2. 등록
     */
    public FeedbackCategory createFeedbackCategory(FeedbackCategory feedbackCategory) {
        // 1. 카테고리 중복 검사
        verifyFeedbackCategory(feedbackCategory.getFeedbackCategoryName());

        // 2. 등록
        return feedbackCategoryRepository.save(feedbackCategory);
    }

    // 카티고리 이름 중복 검사 메서드
    private void verifyFeedbackCategory(String feedbackCategoryName) {
        Optional<FeedbackCategory> optionalFeedbackCategory = feedbackCategoryRepository.findByFeedbackCategoryName(feedbackCategoryName);
        if (optionalFeedbackCategory.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.CATEGORY_EXISTS);
        }
    }

    // 피드백 카테고리 조회 메서드
    public FeedbackCategory findverifiedFeedbackCategory(Long feedbackCategoryId){
        Optional<FeedbackCategory> optionalFeedbackCategory = feedbackCategoryRepository.findById(feedbackCategoryId);
        FeedbackCategory feedbackCategory = optionalFeedbackCategory.orElseThrow(() -> new BusinessLogicException(ExceptionCode.FEEDBACK_CATEGORY_NOT_FOUND));
        return  feedbackCategory;
    }
}

