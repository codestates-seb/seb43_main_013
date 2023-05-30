package com.CreatorConnect.server.board.categories.feedbackcategory.repository;

import com.CreatorConnect.server.board.categories.feedbackcategory.entity.FeedbackCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedbackCategoryRepository extends JpaRepository<FeedbackCategory, Long> {
    Optional<FeedbackCategory> findByFeedbackCategoryName(String feedbackCategoryName);
}