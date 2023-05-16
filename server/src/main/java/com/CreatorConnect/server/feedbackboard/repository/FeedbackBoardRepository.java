package com.CreatorConnect.server.feedbackboard.repository;

import com.CreatorConnect.server.feedbackboard.entity.FeedbackBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackBoardRepository extends JpaRepository<FeedbackBoard, Long> {
    @Query("select f from FeedbackBoard f where f.feedbackCategory.feedbackCategoryId = :feedbackCategoryId")
    Page<FeedbackBoard> findFeedbackBoardsByFeedbackCategoryId(@Param("feedbackCategoryId") long feedbackCategoryId, Pageable pageable);

}
