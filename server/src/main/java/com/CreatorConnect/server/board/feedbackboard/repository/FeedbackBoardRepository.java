package com.CreatorConnect.server.board.feedbackboard.repository;

import com.CreatorConnect.server.board.feedbackboard.entity.FeedbackBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackBoardRepository extends JpaRepository<FeedbackBoard, Long> {

    Page<FeedbackBoard> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

    @Query("select f from FeedbackBoard f where f.feedbackCategory.feedbackCategoryId = :feedbackCategoryId")
    Page<FeedbackBoard> findFeedbackBoardsByFeedbackCategoryId(@Param("feedbackCategoryId") long feedbackCategoryId, Pageable pageable);

    @Query("select f from FeedbackBoard f where f.category.categoryId = :categoryId")
    Page<FeedbackBoard> findFeedbackBoardsByCategoryId(@Param("categoryId") long categoryId, Pageable pageable);

    @Query("select f from FeedbackBoard f where f.feedbackCategory.feedbackCategoryId = :feedbackCategoryId And f.category.categoryId = :categoryId")
    Page<FeedbackBoard> findFeedbackBoardsByFeedbackCategoryIdAndCategoryId(@Param("feedbackCategoryId") long feedbackCategoryId, @Param("categoryId") long categoryId, Pageable pageable);

}
