package com.CreatorConnect.server.board.tag.repository;

import com.CreatorConnect.server.board.promotionboard.entity.PromotionBoard;
import com.CreatorConnect.server.board.tag.entity.TagToPromotionBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagToPromotionBoardRepository extends JpaRepository<TagToPromotionBoard, Long> {
    List<TagToPromotionBoard> findByPromotionBoard(PromotionBoard promotionBoard);
}
