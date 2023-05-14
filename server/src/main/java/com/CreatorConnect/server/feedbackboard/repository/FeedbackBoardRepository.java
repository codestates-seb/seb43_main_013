package com.CreatorConnect.server.feedbackboard.repository;

import com.CreatorConnect.server.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.feedbackcategory.entity.FeedbackCategory;
import com.CreatorConnect.server.freeboard.entity.FreeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackBoardRepository extends JpaRepository<FeedbackBoard, Long> {
    @Query("select f from FeedbackBoard f where f.feedbackCategory.feedbackCategoryId = :feedbackCategoryId")
    Page<FeedbackBoard> findFeedbackBoardsByFeedbackCategoryId(@Param("feedbackCategoryId") long feedbackCategoryId, Pageable pageable);

}
