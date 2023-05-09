package com.CreatorConnect.server.feedbackcategory.repository;

import com.CreatorConnect.server.feedbackcategory.entity.FeedbackCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedbackCategoryRepository extends JpaRepository<FeedbackCategory, Long> {
    Optional<FeedbackCategory> findByFeedbackCategory(String name);
}