package com.CreatorConnect.server.board.comments.comment.repository;

import com.CreatorConnect.server.board.comments.comment.entity.FeedbackComment;
import com.CreatorConnect.server.board.comments.comment.entity.CommentPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackCommentRepository extends JpaRepository<FeedbackComment, CommentPK> {
    @Query("select f from FeedbackComment f where f.feedbackBoard.feedbackBoardId = :feedbackBoardId")
    Page<FeedbackComment> findByFeedbackBoardId(@Param("feedbackBoardId") long feedbackBoardId, Pageable pageable);

}
