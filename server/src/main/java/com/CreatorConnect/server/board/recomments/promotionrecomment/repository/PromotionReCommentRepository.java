package com.CreatorConnect.server.board.recomments.promotionrecomment.repository;

import com.CreatorConnect.server.board.recomments.common.entity.ReCommentPK;
import com.CreatorConnect.server.board.recomments.promotionrecomment.entity.PromotionReComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionReCommentRepository extends JpaRepository<PromotionReComment, ReCommentPK> {
}
