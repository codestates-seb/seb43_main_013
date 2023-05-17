package com.CreatorConnect.server.board.recomments.feedbackrecomment.repository;

import com.CreatorConnect.server.board.recomments.feedbackrecomment.entity.FeedbackReComment;
import com.CreatorConnect.server.board.recomments.common.entity.ReCommentPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackReCommentRepository extends JpaRepository<FeedbackReComment, ReCommentPK> {
}
