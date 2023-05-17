package com.CreatorConnect.server.board.comments.recomment.repository;

import com.CreatorConnect.server.board.comments.recomment.entity.FeedbackReComment;
import com.CreatorConnect.server.board.comments.recomment.entity.ReCommentPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackReCommentRepository extends JpaRepository<FeedbackReComment, ReCommentPK> {
}
