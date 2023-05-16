package com.CreatorConnect.server.tag.repository;

import com.CreatorConnect.server.feedbackboard.entity.FeedbackBoard;
import com.CreatorConnect.server.freeboard.entity.FreeBoard;
import com.CreatorConnect.server.tag.entity.TagToFeedbackBoard;
import com.CreatorConnect.server.tag.entity.TagToFreeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagToFeedbackBoardRepository extends JpaRepository<TagToFeedbackBoard, Long> {
    List<TagToFeedbackBoard> findByFeedbackBoard(FeedbackBoard feedbackBoard);


}

