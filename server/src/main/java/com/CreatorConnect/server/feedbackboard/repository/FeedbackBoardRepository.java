package com.CreatorConnect.server.feedbackboard.repository;

import com.CreatorConnect.server.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.feedbackcategory.entity.FeedbackCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackBoardRepository extends JpaRepository<FeedbackBoard, Long> {
    List<FeedbackBoard> findByFeedbackCategory(FeedbackCategory feedbackCategory);
}
