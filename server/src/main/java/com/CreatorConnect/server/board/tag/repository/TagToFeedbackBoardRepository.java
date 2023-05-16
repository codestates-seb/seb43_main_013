package com.CreatorConnect.server.board.tag.repository;

import com.CreatorConnect.server.board.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.board.tag.entity.TagToFeedbackBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagToFeedbackBoardRepository extends JpaRepository<TagToFeedbackBoard, Long> {
    List<TagToFeedbackBoard> findByFeedbackBoard(FeedbackBoard feedbackBoard);


}