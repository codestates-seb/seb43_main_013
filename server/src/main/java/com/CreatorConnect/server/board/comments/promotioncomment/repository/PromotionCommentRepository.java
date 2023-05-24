package com.CreatorConnect.server.board.comments.promotioncomment.repository;

import com.CreatorConnect.server.board.comments.common.entity.CommentPK;
import com.CreatorConnect.server.board.comments.promotioncomment.entity.PromotionComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PromotionCommentRepository extends JpaRepository<PromotionComment, CommentPK> {
    @Query("select f from PromotionComment f where f.promotionBoard.promotionBoardId = :promotionBoardId")
    Page<PromotionComment> findByPromotionBoardId(@Param("promotionBoardId") long promotionBoardId, Pageable pageable);
}
