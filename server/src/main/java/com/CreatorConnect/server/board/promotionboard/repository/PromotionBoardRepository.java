package com.CreatorConnect.server.board.promotionboard.repository;

import com.CreatorConnect.server.board.categories.category.entity.Category;
import com.CreatorConnect.server.board.promotionboard.entity.PromotionBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionBoardRepository extends JpaRepository<PromotionBoard, Long> {

    List<PromotionBoard> findByCategory(Category category);
    Page<PromotionBoard> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

    @Query("select f from PromotionBoard f where f.category.categoryId = :categoryId")
    Page<PromotionBoard> findPromotionCategoryId(@Param("categoryId") long categoryId, Pageable pageable);
}
