package com.CreatorConnect.server.board.promotionboard.repository;

import com.CreatorConnect.server.board.promotionboard.entity.PromotionBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionBoardRepository extends JpaRepository<PromotionBoard, Long> {
}
